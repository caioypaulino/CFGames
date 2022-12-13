package com.project.cfgames.controllers;

import com.project.cfgames.entities.CarrinhoCompra;
import com.project.cfgames.entities.ItemCarrinho;
import com.project.cfgames.repositories.CarrinhoCompraRepository;
import com.project.cfgames.repositories.ItemCarrinhoRepository;
import com.project.cfgames.services.CarrinhoCompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/carrinhos")
public class CarrinhoCompraController {

    @Autowired
    CarrinhoCompraRepository carrinhoCompraRepository;

    @Autowired
    ItemCarrinhoRepository itemCarrinhoRepository;

    @Autowired
    CarrinhoCompraService carrinhoCompraService;

    // create JPA
    @PostMapping("/save")
    public CarrinhoCompra savePedido(@RequestBody CarrinhoCompra carrinhoCompra) {
        carrinhoCompra.setValorTotal(carrinhoCompraService.valorItens(carrinhoCompra.getItensCarrinho()));
        return carrinhoCompraRepository.save(carrinhoCompra);
    }

    // add item carrinho
    @PutMapping("/{id}/itemcarrinho/")
    public CarrinhoCompra addItem(@PathVariable Long id, @RequestBody ItemCarrinho itemCarrinho) {
        CarrinhoCompra carrinhoCompra = carrinhoCompraRepository.findById(id).get();

        carrinhoCompra.itensPedido(itemCarrinho);
        carrinhoCompra.setValorTotal(carrinhoCompraService.valorItens(carrinhoCompra.getItensCarrinho()));

        itemCarrinhoRepository.save(itemCarrinho);
        return carrinhoCompraRepository.save(carrinhoCompra);
    }

    // remove item carrinho
    @DeleteMapping("{id}/itemcarrinho/{itemId}")
    public ResponseEntity<String> removeItem(@PathVariable Long id, @PathVariable Long itemId) {
        Optional<CarrinhoCompra> carrinhoCompra = carrinhoCompraRepository.findById(id);
        Optional<ItemCarrinho> itemCarrinho = itemCarrinhoRepository.findById(itemId);

        if (carrinhoCompra.isPresent() && itemCarrinho.isPresent()) {
            itemCarrinhoRepository.removeItem(itemId, id);

            carrinhoCompra.get().setValorTotal(carrinhoCompraService.valorItens(carrinhoCompra.get().getItensCarrinho()));
            carrinhoCompraRepository.save(carrinhoCompra.get());

            return ResponseEntity.ok("Item removido com sucesso!");
        } else {
            return ResponseEntity.badRequest().body("Carrinho ou Item não encontrados pelo carrinho_compra_id " + id + " e item_carrinho_id " + itemId);
        }
    }

    // readAll JPA
    @GetMapping("/read")
    public List<CarrinhoCompra> readAllPedido() {
        return carrinhoCompraRepository.findAll();
    }

    // readById JPA
    @GetMapping("/read/{id}")
    public CarrinhoCompra readByIdPedido(@PathVariable Long id) {
        Optional<CarrinhoCompra> pedido = carrinhoCompraRepository.findById(id);
        if (pedido.isPresent()) {
            return pedido.get();
        } else {
            throw new RuntimeException("pedido não encontrado pelo id: " + id);
        }
    }

    // update JPA
    @PutMapping("/update")
    public CarrinhoCompra updateCarrinho(@RequestBody CarrinhoCompra carrinhoCompra) {
        carrinhoCompra.setValorTotal(carrinhoCompraService.valorItens(carrinhoCompra.getItensCarrinho()));
        return carrinhoCompraRepository.save(carrinhoCompra);
    }

    // delete JPA
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCarrinho(@PathVariable Long id) {
        Optional<CarrinhoCompra> carrinhoCompra = carrinhoCompraRepository.findById(id);
        if (carrinhoCompra.isPresent()) {
            itemCarrinhoRepository.deleteItens(id);
            carrinhoCompraRepository.delete(carrinhoCompra.get());
            return ResponseEntity.ok("Carrinho de compra deletado com sucesso!");
        } else {
            return ResponseEntity.badRequest().body("Carrinho de compra não encontrado pelo id: " + id);
        }
    }
}

