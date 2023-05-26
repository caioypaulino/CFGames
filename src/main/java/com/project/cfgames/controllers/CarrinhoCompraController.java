package com.project.cfgames.controllers;

import com.project.cfgames.dtos.mappers.CustomMapper;
import com.project.cfgames.dtos.requests.CarrinhoCompraRequest;
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
    public ResponseEntity<?> readCarrinhoAberto(@RequestHeader(value = "Authorization") String authToken) {
        Cliente cliente = clienteService.getClienteByToken(authToken);

        CarrinhoCompra carrinhoAberto = carrinhoCompraRepository.getReferenceById(clienteRepository.findCarrinhoAberto(cliente.getId()));

        return ResponseEntity.ok().body(carrinhoAberto);
    }

    // alterar carrinho em aberto
    @PutMapping("/update")
    public ResponseEntity<String> updateCarrinho(@RequestHeader(value = "Authorization") String authToken, @RequestBody @Valid CarrinhoCompraRequest request) {
        Cliente cliente = clienteService.getClienteByToken(authToken);

        CarrinhoCompra carrinhoCompra = carrinhoCompraRepository.getReferenceById(clienteRepository.findCarrinhoAberto(cliente.getId()));

        validationCarrinhoCompra.updateAllValidates(request);

        CustomMapper.update(request, carrinhoCompra);

        carrinhoCompra.setValorCarrinho(carrinhoCompraService.valorItens(carrinhoCompra.getItensCarrinho()));
        carrinhoCompra.setPesoTotal(carrinhoCompraService.pesoItens(carrinhoCompra.getItensCarrinho()));

        carrinhoCompraRepository.save(carrinhoCompra);

        return ResponseEntity.ok("Carrinho de Compra atualizado com sucesso!");
    }

    // adicionar item carrinho
    @PutMapping("/add/itemcarrinho")
    public ResponseEntity<String> addItem(@RequestHeader(value = "Authorization") String authToken, @RequestBody @Valid ItemCarrinho itemCarrinho) {
        Cliente cliente = clienteService.getClienteByToken(authToken);

        CarrinhoCompra carrinhoCompra = carrinhoCompraRepository.getReferenceById(clienteRepository.findCarrinhoAberto(cliente.getId()));

        validationCarrinhoCompra.produtoCarrinhoValidate(carrinhoCompra, itemCarrinho);
        validationItemCarrinho.allValidates(itemCarrinho);

        carrinhoCompra.addItensCarrinho(itemCarrinho);
        carrinhoCompra.setValorCarrinho(carrinhoCompraService.valorItens(carrinhoCompra.getItensCarrinho()));
        carrinhoCompra.setPesoTotal(carrinhoCompraService.pesoItens(carrinhoCompra.getItensCarrinho()));

        carrinhoCompraRepository.save(carrinhoCompra);

        return ResponseEntity.ok("Item adicionado com sucesso!");
    }

    // remover item carrinho
    @DeleteMapping("/remove/itemcarrinho/{itemId}")
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
