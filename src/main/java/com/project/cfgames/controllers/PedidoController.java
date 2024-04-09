package com.project.cfgames.controllers;

import com.project.cfgames.clients.responses.FreteResponse;
import com.project.cfgames.dtos.requests.IdRequest;
import com.project.cfgames.entities.*;
import com.project.cfgames.entities.enums.StatusPedido;
import com.project.cfgames.entities.relations.EnderecoCliente;
import com.project.cfgames.repositories.*;
import com.project.cfgames.services.*;
import com.project.cfgames.validations.ValidationPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/pedido")
public class PedidoController {
    @Autowired
    ClienteService clienteService;
    @Autowired
    ClienteRepository clienteRepository;
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
    @Autowired
    CupomService cupomService;
    @Autowired
    DataService dataService;

    // adicionar pediddo
    @PostMapping("/add")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<String> addPedido(@RequestHeader(value = "Authorization") String authToken, @RequestBody @Valid Pedido pedido) {
        try {
            Cliente cliente = clienteService.getClienteByToken(authToken);

            CarrinhoCompra carrinhoCompra = carrinhoCompraRepository.getReferenceById(clienteRepository.findCarrinhoAberto(cliente.getId()));

            pedido.setCliente(cliente);
            pedido.setCarrinhoCompra(carrinhoCompra);

            validationPedido.allValidates(pedido);

            // instanciando objetos para acessar atributos da classe
            EnderecoCliente enderecoCliente = enderecoClienteRepository.getReferenceById(pedido.getEnderecoCliente().getId());

            // set Valor do Frete através da API Melhor Envio
            FreteResponse freteResponse = pedidoService.calcularFrete(enderecoCliente.getEndereco().getCep(), carrinhoCompra.getPesoTotal());
            pedido.setFrete(Float.valueOf(freteResponse.getPrice()));
            pedido.setPrazoDias(freteResponse.getDeliveryTime());

            pedido.setValorTotal(pedidoService.calcularValorTotal(carrinhoCompra.getValorCarrinho(), pedido.getFrete()));
            pedido.setStatus(StatusPedido.EM_PROCESSAMENTO);
            pedido.setDataAtualizacao(dataService.getDateTimeNow());
            pedido.setData(dataService.getDateTimeNow());

            if (pedido.getCartoes() != null && !pedido.getCartoes().isEmpty()) {
                if (validationPedido.valorParcialNullValidate(pedido)) {
                    // set Valor Parcial de cada Cartão
                    pedidoService.valorParcial(pedido);
                }
                else  {
                    validationPedido.valorParcialValidate(pedido);
                }

                // set Valor Parcelas e IdPedido de cada Cartão
                pedidoService.valorParcelas(pedido.getCartoes());
                pedidoService.idPedido(pedido, pedido.getCartoes());
            }
            else {
                Float valorCupons = validationPedido.valorCuponsValidate(pedido);

                if (valorCupons > 0) {
                    cupomService.gerarCupom(cliente, valorCupons);
                }
            }

            pedidoRepository.save(pedido);

            if (pedido.getCupons() != null && !pedido.getCupons().isEmpty()) {
                cupomService.cuponsUsados(pedido.getCupons());
            }
            if (pedido.getCartoes() != null && !pedido.getCartoes().isEmpty()) {
                cartaoPedidoRepository.saveAll(pedido.getCartoes());
            }

            // retorna a primeira resposta
            return ResponseEntity.ok("Pedido #" + pedido.getId() + " adicionado com sucesso!<br></br>Status: Em Processamento<br></br>Status: " + pedidoService.verificarPagamento(pedido).getBody());
        }
        catch (EntityNotFoundException ex) {
            return ResponseEntity.badRequest().body("Endereço Cliente não encontrado.");
        }
        catch (InvalidDataAccessApiUsageException ex) {
            return ResponseEntity.badRequest().body("Nenhum Carrinho de Compra em Aberto encontrado.");
        }
    }

    // calculo frete API com Carrinho de Compra em Aberto e Endereço Cliente
    @PostMapping("/calcular/frete")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<?> calcularFrete(@RequestHeader(value = "Authorization") String authToken, @RequestBody IdRequest request) {
        try {
            Cliente cliente = clienteService.getClienteByToken(authToken);

            CarrinhoCompra carrinhoCompra = carrinhoCompraRepository.getReferenceById(clienteRepository.findCarrinhoAberto(cliente.getId()));

            EnderecoCliente enderecoCliente = enderecoClienteRepository.getReferenceById(request.getId());

            return ResponseEntity.ok().body(pedidoService.calcularFrete(enderecoCliente.getEndereco().getCep(), carrinhoCompra.getPesoTotal()));
        }
        catch (EntityNotFoundException ex) {
            return ResponseEntity.badRequest().body("Endereço Cliente não encontrado.");
        }
        catch (InvalidDataAccessApiUsageException ex) {
            return ResponseEntity.badRequest().body("Nenhum Carrinho de Compra em Aberto encontrado.");
        }
    }

    // calculo frete API
    @GetMapping("/calcular/frete/{cepDestino}/{peso}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public FreteResponse calcularFrete(@PathVariable String cepDestino, @PathVariable Integer peso) {
        return pedidoService.calcularFrete(cepDestino, peso);
    }
}
