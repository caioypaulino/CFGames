package com.project.cfgames.controllers;

import com.project.cfgames.dtos.mappers.CustomMapper;
import com.project.cfgames.dtos.requests.CarrinhoCompraRequest;
import com.project.cfgames.entities.CarrinhoCompra;
import com.project.cfgames.entities.ItemCarrinho;
import com.project.cfgames.repositories.CarrinhoCompraRepository;
import com.project.cfgames.repositories.ItemCarrinhoRepository;
import com.project.cfgames.repositories.PedidoRepository;
import com.project.cfgames.services.CarrinhoCompraService;
import com.project.cfgames.validations.ValidationCarrinhoCompra;
import com.project.cfgames.validations.ValidationItemCarrinho;
import org.modelmapper.MappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/carrinhos")
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

    // create JPA
    @PostMapping("/save")
    public ResponseEntity<String> saveCarrinho(@RequestBody @Valid CarrinhoCompra carrinhoCompra) {
        validationCarrinhoCompra.allValidates(carrinhoCompra);

        carrinhoCompra.setValorCarrinho(carrinhoCompraService.valorItens(carrinhoCompra.getItensCarrinho()));
        carrinhoCompra.setPesoTotal(carrinhoCompraService.pesoItens(carrinhoCompra.getItensCarrinho()));

        carrinhoCompraRepository.save(carrinhoCompra);

        return ResponseEntity.ok("Carrinho de Compra adicionado com sucesso!");
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
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateCarrinho(@PathVariable Long id, @RequestBody @Valid CarrinhoCompraRequest request) {
        try {
            CarrinhoCompra carrinhoCompra = carrinhoCompraRepository.getReferenceById(id);

            validationCarrinhoCompra.updateAllValidates(request);

            CustomMapper.update(request, carrinhoCompra);

            carrinhoCompra.setValorCarrinho(carrinhoCompraService.valorItens(carrinhoCompra.getItensCarrinho()));
            carrinhoCompra.setPesoTotal(carrinhoCompraService.pesoItens(carrinhoCompra.getItensCarrinho()));

            carrinhoCompraRepository.save(carrinhoCompra);

            return ResponseEntity.ok("Carrinho de Compra atualizado com sucesso!");
        }
        catch (EntityNotFoundException | MappingException ex) {
            return ResponseEntity.badRequest().body("Carrinho de Compra não encontrado pelo id: " + id);
        }
    }

    // add item carrinho
    @PutMapping("/{id}/itemcarrinho/")
    public ResponseEntity<String> addItem(@PathVariable Long id, @RequestBody @Valid ItemCarrinho itemCarrinho) {
        try {
            CarrinhoCompra carrinhoCompra = carrinhoCompraRepository.getReferenceById(id);

            validationCarrinhoCompra.produtoCarrinhoValidate(carrinhoCompra, itemCarrinho);
            validationItemCarrinho.allValidates(itemCarrinho);

            carrinhoCompra.addItensCarrinho(itemCarrinho);
            carrinhoCompra.setValorCarrinho(carrinhoCompraService.valorItens(carrinhoCompra.getItensCarrinho()));
            carrinhoCompra.setPesoTotal(carrinhoCompraService.pesoItens(carrinhoCompra.getItensCarrinho()));

            carrinhoCompraRepository.save(carrinhoCompra);

            return ResponseEntity.ok("Item adicionado com sucesso!");
        }
        catch (EntityNotFoundException ex) {
            return ResponseEntity.badRequest().body("Carrinho não encontrado pelo Carrinho Compra id: " + id);
        }
    }

    // remove item carrinho
    @DeleteMapping("{id}/itemcarrinho/{itemId}")
    public ResponseEntity<String> removeItem(@PathVariable Long id, @PathVariable Long itemId) {
        try {
            CarrinhoCompra carrinhoCompra = carrinhoCompraRepository.getReferenceById(id);
            ItemCarrinho itemCarrinho = itemCarrinhoRepository.getReferenceById(itemId);

            if (itemCarrinhoRepository.selectItemByCarrinhoAndItem(id, itemId) != null)
            {
                itemCarrinhoRepository.removeItem(itemCarrinho.getId(), id);

                // atualiza carrinho (valor e peso)
                carrinhoCompra.setValorCarrinho(carrinhoCompraService.valorItens(carrinhoCompra.getItensCarrinho()));
                carrinhoCompra.setPesoTotal(carrinhoCompraService.pesoItens(carrinhoCompra.getItensCarrinho()));

                carrinhoCompraRepository.save(carrinhoCompra);

                return ResponseEntity.ok("Item removido com sucesso!");
            }
            else {
                return ResponseEntity.badRequest().body("Item não encontrado pelo Item Carrinho Id: " + itemId);
            }
        }
        catch (EntityNotFoundException ex) {
            return ResponseEntity.badRequest().body("Carrinho de Compra ou item do Carrinho não encontrados pelo Carrinho de Compra id: " + id + " e Item Carrinho id: " + itemId);
        }
    }

    // delete JPA
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCarrinho(@PathVariable Long id) {
        try {
            if (pedidoRepository.selectPedidoByCarrinho(id) != null) {
                return ResponseEntity.badRequest().body("Carrinho de Compra não pode ser deletado por estar relacionado a um pedido! " +
                                                        "id pedido: " + pedidoRepository.selectPedidoByCarrinho(id));
            }
            else {
                // delete itens carrinho de compra + delete carrinho de compra
                itemCarrinhoRepository.deleteItens(id);
                carrinhoCompraRepository.deleteById(id);

                return ResponseEntity.ok("Carrinho de Compra deletado com sucesso!");
            }
        }
        catch (EmptyResultDataAccessException ex) {
            return ResponseEntity.badRequest().body("Carrinho de Compra não encontrado pelo id: " + id);
        }
    }
}

