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
import com.project.cfgames.validations.exceptions.CustomValidationException;
import com.project.cfgames.validations.handlers.HandlerCustomValidationsExceptions;
import com.project.cfgames.validations.handlers.HandlerValidationsExceptions;
import org.modelmapper.MappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/carrinhos")
public class CarrinhoCompraController {

    @Autowired
    CarrinhoCompraRepository carrinhoCompraRepository;
    @Autowired
    ItemCarrinhoRepository itemCarrinhoRepository;
    @Autowired
    PedidoRepository pedidoRepository;
    @Autowired
    CarrinhoCompraService carrinhoCompraService;
    @Autowired
    ValidationCarrinhoCompra validationCarrinhoCompra;
    @Autowired
    ValidationItemCarrinho validationItemCarrinho;

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
            // instanciando objetos para acessar atributos da classe
            CarrinhoCompra carrinhoCompra = carrinhoCompraRepository.getReferenceById(id);

            // validação de dados
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
            Long pedidoId = pedidoRepository.selectPedidoByCarrinho(id);

            if (pedidoId != null) {
                return ResponseEntity.badRequest().body("Carrinho de Compra não pode ser deletado por estar relacionado a um pedido! id pedido: " + pedidoId);
            }
            else {
                // delete itens carrinho de compra
                itemCarrinhoRepository.deleteItens(id);
                // delete carrinho de compra
                carrinhoCompraRepository.deleteById(id);

                return ResponseEntity.ok("Carrinho de Compra deletado com sucesso!");
            }
        }
        catch (EmptyResultDataAccessException ex) {
            return ResponseEntity.badRequest().body("Carrinho de Compra não encontrado pelo id: " + id);
        }
    }

    // handler @validation exception
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationsExceptions(MethodArgumentNotValidException exception) {
        return HandlerValidationsExceptions.handler(exception);
    }

    // handler @validation exception Item Carrinho
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public Map<String, String> handleValidationsExceptions(ConstraintViolationException exception) {
        return HandlerValidationsExceptions.handler(exception);
    }

    // handler custom validation exception
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CustomValidationException.class)
    public String handleCustomValidationsExceptions (CustomValidationException exception){
        return HandlerCustomValidationsExceptions.handler(exception);
    }
}

