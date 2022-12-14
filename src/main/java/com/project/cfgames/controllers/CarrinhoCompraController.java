package com.project.cfgames.controllers;

import com.project.cfgames.entities.CarrinhoCompra;
import com.project.cfgames.entities.ItemCarrinho;
import com.project.cfgames.repositories.CarrinhoCompraRepository;
import com.project.cfgames.repositories.ItemCarrinhoRepository;
import com.project.cfgames.services.CarrinhoCompraService;
import com.project.cfgames.strategies.StrategyCarrinhoCompra;
import org.checkerframework.checker.units.qual.A;
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

    @Autowired
    StrategyCarrinhoCompra strategyCarrinhoCompra;

    // create JPA
    @PostMapping("/save")
    public ResponseEntity<String> saveCarrinho(@RequestBody CarrinhoCompra carrinhoCompra) {
        // validação de dados
        if (strategyCarrinhoCompra.allValidates(carrinhoCompra)){
            carrinhoCompra.setValorTotal(carrinhoCompraService.valorItens(carrinhoCompra.getItensCarrinho()));
            carrinhoCompraRepository.save(carrinhoCompra);

            return ResponseEntity.ok("Carrinho de Compra adicionado com sucesso!");
        }
        else {
            return ResponseEntity.badRequest().body("Erro Validação");
        }
    }

    // add item carrinho
    @PutMapping("/{id}/itemcarrinho/")
    public ResponseEntity<String> addItem(@PathVariable Long id, @RequestBody ItemCarrinho itemCarrinho) {
        Optional<CarrinhoCompra> carrinhoCompra = carrinhoCompraRepository.findById(id);

        if (carrinhoCompra.isPresent()){
            // validação de dados
            if (strategyCarrinhoCompra.allValidates(carrinhoCompra.get())){
                carrinhoCompra.get().itensPedido(itemCarrinho);
                carrinhoCompra.get().setValorTotal(carrinhoCompraService.valorItens(carrinhoCompra.get().getItensCarrinho()));

                carrinhoCompraRepository.save(carrinhoCompra.get());
                return ResponseEntity.ok("Item adicionado com sucesso!");
            }
            else {
                return ResponseEntity.badRequest().body("Erro Validação Item");
            }
        }
        else {
            return ResponseEntity.badRequest().body("Carrinho não encontrado pelo carrinho_compra_id " + id);
        }
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
        }
        else {
            return ResponseEntity.badRequest().body("Carrinho ou Item não encontrados pelo carrinho_compra_id " + id + " e item_carrinho_id " + itemId);
        }
    }

    // readAll JPA
    @GetMapping("/read")
    public List<CarrinhoCompra> readAllCarrinho() {
        return carrinhoCompraRepository.findAll();
    }

    // readById JPA
    @GetMapping("/read/{id}")
    public CarrinhoCompra readByIdCarrinho(@PathVariable Long id) {
        Optional<CarrinhoCompra> carrinhoCompra = carrinhoCompraRepository.findById(id);
        if (carrinhoCompra.isPresent()) {
            return carrinhoCompra.get();
        } else {
            throw new RuntimeException("Carrinho de compra não encontrado pelo id: " + id);
        }
    }

    // update JPA
    @PutMapping("/update")
    public ResponseEntity<String> updateCarrinho(@RequestBody CarrinhoCompra carrinhoCompra) {
        // validação de dados
        if (strategyCarrinhoCompra.allValidates(carrinhoCompra)){
            carrinhoCompra.setValorTotal(carrinhoCompraService.valorItens(carrinhoCompra.getItensCarrinho()));
            carrinhoCompraRepository.save(carrinhoCompra);

            return ResponseEntity.ok("Carrinho de Compra atualizado com sucesso!");
        }
        else {
            return ResponseEntity.badRequest().body("Erro Validação");
        }
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

