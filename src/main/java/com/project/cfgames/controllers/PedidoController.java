package com.project.cfgames.controllers;

import com.project.cfgames.clients.responses.FreteResponse;
import com.project.cfgames.entities.CarrinhoCompra;
import com.project.cfgames.entities.Pedido;
import com.project.cfgames.entities.enums.StatusPedido;
import com.project.cfgames.entities.relations.EnderecoCliente;
import com.project.cfgames.repositories.CarrinhoCompraRepository;
import com.project.cfgames.repositories.CartaoPedidoRepository;
import com.project.cfgames.repositories.EnderecoClienteRepository;
import com.project.cfgames.repositories.PedidoRepository;
import com.project.cfgames.requests.PedidoRequest;
import com.project.cfgames.services.PedidoService;
import com.project.cfgames.strategies.StrategyPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    @Autowired
    StrategyPedido strategyPedido;
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
    public ResponseEntity<String> savePedido(@RequestBody Pedido pedido) {
        try {
            // instanciando objetos para acessar atributos da classe
            EnderecoCliente enderecoCliente = enderecoClienteRepository.getReferenceById(pedido.getEnderecoCliente().getId());
            CarrinhoCompra carrinhoCompra = carrinhoCompraRepository.getReferenceById(pedido.getCarrinhoCompra().getId());

            // set Valor do Frete através da API FeignClient (FreteClient) e ResponseDTO (FreteResponse)
            FreteResponse freteResponse = pedidoService.calcularFrete(enderecoCliente.getEndereco().getCep(), carrinhoCompra.getPesoTotal());
            pedido.setFrete(Float.valueOf(freteResponse.getValorsedex().replace(",", ".")));

            pedido.setValorTotal(pedidoService.calcularValorTotal(carrinhoCompra.getValorCarrinho(), pedido.getFrete()));
            pedido.setData(pedidoService.getDateTimeNow());
            pedido.setStatus(StatusPedido.EM_PROCESSAMENTO);
            pedido.setDataAtualizacao(pedidoService.getDateTimeNow());

            //set Valor Parcial de cada Cartão
            if (strategyPedido.valorParcialNullValidate(pedido)) {
                pedidoService.valorParcial(pedido.getValorTotal(), pedido.getCartoes());
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
    public ResponseEntity<?> updatePedido(@PathVariable Long id, @RequestBody PedidoRequest pedidoRequest) {
        try {
            Pedido pedido = pedidoRepository.getReferenceById(id);

            pedido.setStatus(pedidoRequest.getStatusPedido());
            pedido.setDataAtualizacao(pedidoService.getDateTimeNow());

            pedidoRepository.save(pedido);
            return ResponseEntity.ok("Pedido atualizado com sucesso!");
        }
        catch(EntityNotFoundException e) {
            return ResponseEntity.badRequest().body("Pedido não encontrado pelo id: " + id);
        }
    }

    // delete JPA
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePedido(@PathVariable Long id) {
        try {
            Pedido pedido = pedidoRepository.getReferenceById(id);

            // delete cartoes do pedido
            cartaoPedidoRepository.deleteCartoes(id);
            // delete pedido
            pedidoRepository.delete(pedido);
            return ResponseEntity.ok("Pedido deletado com sucesso!");
        }
        catch(EntityNotFoundException e) {
            return ResponseEntity.badRequest().body("Pedido não encontrado pelo id: " + id);
        }
    }

    @GetMapping("/frete/{cepDestino}/{peso}")
    public FreteResponse calcularFrete(@PathVariable String cepDestino, @PathVariable Integer peso) {
        return pedidoService.calcularFrete(cepDestino, peso);
    }
}
