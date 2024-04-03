package com.project.cfgames.controllers;

import com.project.cfgames.dtos.requests.ItemCarrinhoRequest;
import com.project.cfgames.entities.CarrinhoCompra;
import com.project.cfgames.entities.Cliente;
import com.project.cfgames.entities.ItemCarrinho;
import com.project.cfgames.repositories.CarrinhoCompraRepository;
import com.project.cfgames.repositories.ClienteRepository;
import com.project.cfgames.repositories.ItemCarrinhoRepository;
import com.project.cfgames.repositories.PedidoRepository;
import com.project.cfgames.services.CarrinhoCompraService;
import com.project.cfgames.services.ClienteService;
import com.project.cfgames.validations.ValidationCarrinhoCompra;
import com.project.cfgames.validations.ValidationItemCarrinho;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/carrinhodecompra")
public class CarrinhoCompraController {
    @Autowired
    ValidationCarrinhoCompra validationCarrinhoCompra;
    @Autowired
    CarrinhoCompraService carrinhoCompraService;
    @Autowired
    CarrinhoCompraRepository carrinhoCompraRepository;
    @Autowired
    ValidationItemCarrinho validationItemCarrinho;
    @Autowired
    ItemCarrinhoRepository itemCarrinhoRepository;
    @Autowired
    PedidoRepository pedidoRepository;
    @Autowired
    ClienteService clienteService;
    @Autowired
    ClienteRepository clienteRepository;

    // adicionar carrinho
    @PostMapping("/add")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<String> addCarrinho(@RequestHeader(value = "Authorization") String authToken, @RequestBody @Valid CarrinhoCompra carrinhoCompra) {
        Cliente cliente = clienteService.getClienteByToken(authToken);

        carrinhoCompra.setCliente(cliente);

        validationCarrinhoCompra.allValidates(carrinhoCompra);

        carrinhoCompra.setValorCarrinho(carrinhoCompraService.valorItens(carrinhoCompra.getItensCarrinho()));
        carrinhoCompra.setPesoTotal(carrinhoCompraService.pesoItens(carrinhoCompra.getItensCarrinho()));

        carrinhoCompraRepository.save(carrinhoCompra);

        return ResponseEntity.ok("Carrinho de Compra adicionado com sucesso!");
    }

    // mostrar carrinho em aberto
    @GetMapping("/read")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<?> readCarrinhoAberto(@RequestHeader(value = "Authorization") String authToken) {
        Cliente cliente = clienteService.getClienteByToken(authToken);

        CarrinhoCompra carrinhoAberto = carrinhoCompraRepository.getReferenceById(clienteRepository.findCarrinhoAberto(cliente.getId()));

        return ResponseEntity.ok().body(carrinhoAberto);
    }

    // alterar carrinho em aberto
    @PutMapping("/update")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<String> updateCarrinho(@RequestHeader(value = "Authorization") String authToken, @RequestBody @Valid ItemCarrinhoRequest request) {
        Cliente cliente = clienteService.getClienteByToken(authToken);

        CarrinhoCompra carrinhoCompra = carrinhoCompraRepository.getReferenceById(clienteRepository.findCarrinhoAberto(cliente.getId()));

        validationCarrinhoCompra.updateQuantidadeValidate(request);

        // se produto já existe no carrinho de compra
        for (ItemCarrinho item: carrinhoCompra.getItens()) {
            if (Objects.equals(item.getId(), request.getItemCarrinhoId())) {
                // alterando quantidade item carrinho
                item.setQuantidade(request.getQuantidade());

                itemCarrinhoRepository.save(item);
            }
        }

        carrinhoCompra.setValorCarrinho(carrinhoCompraService.valorItens(carrinhoCompra.getItensCarrinho()));
        carrinhoCompra.setPesoTotal(carrinhoCompraService.pesoItens(carrinhoCompra.getItensCarrinho()));

        carrinhoCompraRepository.save(carrinhoCompra);

        return ResponseEntity.ok("Carrinho de Compra atualizado com sucesso!");
    }

    // adicionar item carrinho
    @PutMapping("/add/itemcarrinho")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<String> addItem(@RequestHeader(value = "Authorization") String authToken, @RequestBody @Valid ItemCarrinho itemCarrinho) {
        Cliente cliente = clienteService.getClienteByToken(authToken);

        CarrinhoCompra carrinhoCompra = carrinhoCompraRepository.getReferenceById(clienteRepository.findCarrinhoAberto(cliente.getId()));

        // se produto já existe no carrinho de compra
        if (validationCarrinhoCompra.produtoCarrinhoValidate(carrinhoCompra, itemCarrinho)) {
            for (ItemCarrinho item: carrinhoCompra.getItens()) {
                if (Objects.equals(item.getProduto().getId(), itemCarrinho.getProduto().getId())) {
                    // alterando quantidade item carrinho
                    Integer novaQuantidade = item.getQuantidade() + itemCarrinho.getQuantidade();
                    item.setQuantidade(novaQuantidade);

                    itemCarrinhoRepository.save(item);
                }
            }
        }
        else {
            validationItemCarrinho.allValidates(itemCarrinho);
            carrinhoCompra.addItensCarrinho(itemCarrinho);
        }

        carrinhoCompra.setValorCarrinho(carrinhoCompraService.valorItens(carrinhoCompra.getItensCarrinho()));
        carrinhoCompra.setPesoTotal(carrinhoCompraService.pesoItens(carrinhoCompra.getItensCarrinho()));

        carrinhoCompraRepository.save(carrinhoCompra);

        return ResponseEntity.ok("Item adicionado com sucesso!");
    }

    // remover item carrinho
    @DeleteMapping("/remove/itemcarrinho/{itemId}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<String> removeItem(@RequestHeader(value = "Authorization") String authToken, @PathVariable Long itemId) {
        Cliente cliente = clienteService.getClienteByToken(authToken);

        CarrinhoCompra carrinhoCompra = carrinhoCompraRepository.getReferenceById(clienteRepository.findCarrinhoAberto(cliente.getId()));
        ItemCarrinho itemCarrinho = itemCarrinhoRepository.getReferenceById(itemId);

        if (itemCarrinhoRepository.selectItemByCarrinhoAndItem(carrinhoCompra.getId(), itemId) != null)
        {
            itemCarrinhoRepository.removeItem(itemCarrinho.getId(), carrinhoCompra.getId());

            carrinhoCompra.setValorCarrinho(carrinhoCompraService.valorItens(carrinhoCompra.getItensCarrinho()));
            carrinhoCompra.setPesoTotal(carrinhoCompraService.pesoItens(carrinhoCompra.getItensCarrinho()));

            carrinhoCompraRepository.save(carrinhoCompra);

            return ResponseEntity.ok("Item removido com sucesso!");
        }
        else {
            return ResponseEntity.badRequest().body("Item não encontrado pelo Item Carrinho Id: " + itemId);
        }
    }

    // delete JPA
    @DeleteMapping("/delete")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<String> deleteCarrinho(@RequestHeader(value = "Authorization") String authToken) {
        Cliente cliente = clienteService.getClienteByToken(authToken);

        CarrinhoCompra carrinhoCompra = carrinhoCompraRepository.getReferenceById(clienteRepository.findCarrinhoAberto(cliente.getId()));

        if (pedidoRepository.selectPedidoByCarrinho(carrinhoCompra.getId()) == null) {
            // delete itens carrinho de compra + delete carrinho de compra
            itemCarrinhoRepository.deleteItens(carrinhoCompra.getId());
            carrinhoCompraRepository.deleteById(carrinhoCompra.getId());

            return ResponseEntity.ok("Carrinho de Compra deletado com sucesso!");
        }
        else {
            return ResponseEntity.badRequest().body("Carrinho de Compra não pode ser deletado por estar relacionado a um pedido! " +
                    "id pedido: " + pedidoRepository.selectPedidoByCarrinho(carrinhoCompra.getId()));
        }
    }

    // handler carrinho aberto id = null -> [clienteRepository.findCarrinhoAberto(cliente.getId())] exception
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    public String handleCustomValidationsExceptions (){
        return "Nenhum Carrinho de Compra em Aberto encontrado.";
    }
}
