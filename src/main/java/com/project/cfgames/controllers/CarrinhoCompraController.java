package com.project.cfgames.controllers;

import com.project.cfgames.entities.CarrinhoCompra;
import com.project.cfgames.entities.ItemCarrinho;
import com.project.cfgames.entities.Pedido;
import com.project.cfgames.repositories.CarrinhoCompraRepository;
import com.project.cfgames.repositories.ItemCarrinhoRepository;
import com.project.cfgames.services.CarrinhoCompraService;
import com.project.cfgames.strategies.StrategyCarrinhoCompra;
import feign.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
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
            carrinhoCompra.setValorCarrinho(carrinhoCompraService.valorItens(carrinhoCompra.getItensCarrinho()));
            carrinhoCompra.setPesoTotal(carrinhoCompraService.pesoItens(carrinhoCompra.getItensCarrinho()));
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
        try {
            // instanciando objetos para acessar atributos da classe
            CarrinhoCompra carrinhoCompra = carrinhoCompraRepository.getReferenceById(id);

            // validação de dados
            if (strategyCarrinhoCompra.allValidates(carrinhoCompra)){
                carrinhoCompra.itensPedido(itemCarrinho);
                carrinhoCompra.setValorCarrinho(carrinhoCompraService.valorItens(carrinhoCompra.getItensCarrinho()));
                carrinhoCompra.setPesoTotal(carrinhoCompraService.pesoItens(carrinhoCompra.getItensCarrinho()));

                carrinhoCompraRepository.save(carrinhoCompra);
                return ResponseEntity.ok("Item adicionado com sucesso!");
            }
            else {
                return ResponseEntity.badRequest().body("Erro Validação Item");
            }
        }
        catch(EntityNotFoundException e) {
            return ResponseEntity.badRequest().body("Carrinho não encontrado pelo carrinho_compra_id " + id);
        }
    }

    // remove item carrinho
    @DeleteMapping("{id}/itemcarrinho/{itemId}")
    public ResponseEntity<String> removeItem(@PathVariable Long id, @PathVariable Long itemId) {
        try {
            CarrinhoCompra carrinhoCompra = carrinhoCompraRepository.getReferenceById(id);
            ItemCarrinho itemCarrinho = itemCarrinhoRepository.getReferenceById(itemId);

            // validação item carrinho
                itemCarrinhoRepository.removeItem(itemCarrinho.getId(), id);

                carrinhoCompra.setValorCarrinho(carrinhoCompraService.valorItens(carrinhoCompra.getItensCarrinho()));
                carrinhoCompraRepository.save(carrinhoCompra);

                return ResponseEntity.ok("Item removido com sucesso!");
        }
        catch(EntityNotFoundException e) {
            return ResponseEntity.badRequest().body("Carrinho de Compra ou item do Carrinho não encontrados pelo carrinho_compra_id " + id + "item_carrinho_id " + itemId);
        }
    }

    // readAll JPA
    @GetMapping("/read")
    public List<CarrinhoCompra> readAllCarrinho() {
        return carrinhoCompraRepository.findAll();
    }

    // readById JPA
    @GetMapping("/read/{id}")
    public ResponseEntity<?> readByIdCarrinho(@PathVariable Long id) {
        Optional<CarrinhoCompra> carrinhoCompra = carrinhoCompraRepository.findById(id);

        if (carrinhoCompra.isPresent()) {
            return ResponseEntity.ok().body(carrinhoCompra.get());
        }
        else {
            return ResponseEntity.badRequest().body("Carrinho de Compra não encontrado pelo id: " + id);
        }
    }

    // update JPA
    @PutMapping("/update")
    public ResponseEntity<String> updateCarrinho(@RequestBody CarrinhoCompra carrinhoCompra) {
        // validação de dados
        if (strategyCarrinhoCompra.allValidates(carrinhoCompra)){
            carrinhoCompra.setValorCarrinho(carrinhoCompraService.valorItens(carrinhoCompra.getItensCarrinho()));
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
        try {
            CarrinhoCompra carrinhoCompra = carrinhoCompraRepository.getReferenceById(id);
            Long pedidoId = carrinhoCompraRepository.selectPedidoId(id);

            if (pedidoId != null) {
                return ResponseEntity.badRequest().body("Carrinho de Compra não pode ser deletado por estar relacionado a um pedido! id pedido: " + pedidoId);
            }
            else {
                // delete itens carrinho de compra
                itemCarrinhoRepository.deleteItens(id);
                // delete carrinho de compra
                carrinhoCompraRepository.delete(carrinhoCompra);

                return ResponseEntity.ok("Carrinho de Compra deletado com sucesso!");
            }
        }
        catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body("Carrinho de Compra não encontrado pelo id: " + id);
        }
    }
}

