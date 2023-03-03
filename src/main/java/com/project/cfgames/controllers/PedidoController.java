package com.project.cfgames.controllers;

import com.project.cfgames.clients.responses.FreteResponse;
import com.project.cfgames.dtos.mappers.CustomMapper;
import com.project.cfgames.dtos.requests.PedidoRequest;
import com.project.cfgames.entities.CarrinhoCompra;
import com.project.cfgames.entities.Pedido;
import com.project.cfgames.entities.enums.StatusPedido;
import com.project.cfgames.entities.relations.EnderecoCliente;
import com.project.cfgames.repositories.CarrinhoCompraRepository;
import com.project.cfgames.repositories.CartaoPedidoRepository;
import com.project.cfgames.repositories.EnderecoClienteRepository;
import com.project.cfgames.repositories.PedidoRepository;
import com.project.cfgames.services.PedidoService;
import com.project.cfgames.validations.ValidationPedido;
import com.project.cfgames.validations.exceptions.CustomValidationException;
import com.project.cfgames.validations.handlers.HandlerCustomValidationsExceptions;
import com.project.cfgames.validations.handlers.HandlerValidationsExceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    @Autowired
    ValidationPedido validationPedido;
    @Autowired
    PedidoService pedidoService;
    @Autowired
    PedidoRepository pedidoRepository;
    @Autowired
    EnderecoClienteRepository enderecoClienteRepository;
    @Autowired
    CarrinhoCompraRepository carrinhoCompraRepository;
    @Autowired
    CartaoPedidoRepository cartaoPedidoRepository;

    // create JPA
    @PostMapping("/save")
    public ResponseEntity<String> savePedido(@RequestBody @Valid Pedido pedido) {
        try {
            validationPedido.allValidates(pedido);

            // instanciando objetos para acessar atributos da classe
            EnderecoCliente enderecoCliente = enderecoClienteRepository.getReferenceById(pedido.getEnderecoCliente().getId());
            CarrinhoCompra carrinhoCompra = carrinhoCompraRepository.getReferenceById(pedido.getCarrinhoCompra().getId());

            // set Valor do Frete através da API FeignClient (FreteClient) e ResponseDTO (FreteResponse)
            FreteResponse freteResponse = pedidoService.calcularFrete(enderecoCliente.getEndereco().getCep(), carrinhoCompra.getPesoTotal());
            pedido.setFrete(Float.valueOf((freteResponse.getValorsedex().replace(",", "."))));

            pedido.setValorTotal(pedidoService.calcularValorTotal(carrinhoCompra.getValorCarrinho(), pedido.getFrete()));
            pedido.setStatus(StatusPedido.EM_PROCESSAMENTO);
            pedido.setDataAtualizacao(pedidoService.getDateTimeNow());
            pedido.setData(pedidoService.getDateTimeNow());

            if (validationPedido.valorParcialNullValidate(pedido)) {
                // set Valor Parcial de cada Cartão
                pedidoService.valorParcial(pedido.getValorTotal(), pedido.getCartoes());
            }
            else {
                validationPedido.valorParcialValidate(pedido);
            }

            // set Valor Parcelas e IdPedido de cada Cartão
            pedidoService.valorParcelas(pedido.getCartoes());
            pedidoService.idPedido(pedido, pedido.getCartoes());

            pedidoRepository.save(pedido);
            cartaoPedidoRepository.saveAll(pedido.getCartoes());

            return ResponseEntity.ok("Pedido adicionado com sucesso!");
        }
        catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body("Carrinho de Compra ou Endereço do Cliente não encontrados!");
        }
    }

    // readAll JPA
    @GetMapping("/read")
    public List<Pedido> readAllPedido() {
        return pedidoRepository.findAll();
    }

    // readById JPA
    @GetMapping("/read/{id}")
    public ResponseEntity<?> readByIdPedido(@PathVariable Long id) {
        Optional<Pedido> pedido = pedidoRepository.findById(id);

        if (pedido.isPresent()) {
            return ResponseEntity.ok().body(pedido.get());
        }
        else {
            return ResponseEntity.badRequest().body("Pedido não encontrado pelo id: " + id);
        }
    }

    // update JPA
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updatePedido(@PathVariable Long id, @RequestBody PedidoRequest request) {
        try {
            Pedido pedido = pedidoRepository.getReferenceById(id);

            CustomMapper.update(request, pedido);
            pedido.setDataAtualizacao(pedidoService.getDateTimeNow());

            // "gambiarra" :C
            if (request.getStatusPedido() != null) {
                pedido.setStatus(request.getStatusPedido());
            }

            pedidoRepository.save(pedido);

            return ResponseEntity.ok().body("Pedido atualizado com sucesso!");
        }
        catch (EntityNotFoundException ex) {
            return ResponseEntity.badRequest().body("Pedido não encontrado pelo id: " + id);
        }
    }

    // delete JPA
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePedido(@PathVariable Long id) {
        try {
            cartaoPedidoRepository.deleteCartoes(id);
            pedidoRepository.deleteById(id);

            return ResponseEntity.ok().body("Pedido deletado com sucesso!");
        }
        catch (EmptyResultDataAccessException ex) {
            return ResponseEntity.badRequest().body("Pedido não encontrado pelo id: " + id);
        }
    }

    // calculo frete API
    @GetMapping("/frete/{cepDestino}/{peso}")
    public FreteResponse calcularFrete(@PathVariable String cepDestino, @PathVariable Integer peso) {
        return pedidoService.calcularFrete(cepDestino, peso);
    }

    // handler @validation exception
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationsExceptions(MethodArgumentNotValidException exception) {
        return HandlerValidationsExceptions.handler(exception);
    }

    // handler custom validation exception
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CustomValidationException.class)
    public String handleCustomValidationsExceptions (CustomValidationException exception){
        return HandlerCustomValidationsExceptions.handler(exception);
    }

    // handler Enum type Json exception
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public String handleCustomValidationsExceptions (){
        return "Status Pedido inválido (Ex:\n0- EM_PROCESSAMENTO,\n1- APROVADO,\n2- EM_TRANSITO,\n3- ENTREGUE,\n4- EM_TROCA,\n5- TROCA_AUTORIZADA,\n6- REPROVADO).";
    }
}
