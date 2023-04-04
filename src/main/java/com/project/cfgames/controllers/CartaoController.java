package com.project.cfgames.controllers;

import com.project.cfgames.dtos.mappers.CustomMapper;
import com.project.cfgames.dtos.requests.CartaoRequest;
import com.project.cfgames.entities.Cartao;
import com.project.cfgames.repositories.CartaoRepository;
import com.project.cfgames.services.CartaoService;
import com.project.cfgames.validations.ValidationCartao;
import org.modelmapper.MappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cartoes")
public class CartaoController {
    @Autowired
    ValidationCartao validationCartao;
    @Autowired
    CartaoService cartaoService;
    @Autowired
    CartaoRepository cartaoRepository;

    // create JPA
    @PostMapping("/save")
    public ResponseEntity<String> saveCartao(@RequestBody @Valid Cartao cartao){
        validationCartao.allValidates(cartao);

        cartao.setBandeira(cartaoService.bandeiraCartao(cartao));

        cartaoRepository.save(cartao);

        return ResponseEntity.ok().body("Cartão de Crédito adicionado com sucesso!");
    }

    // readAll JPA
    @GetMapping("/read") @RolesAllowed("ROLE_ADMIN")
    public List<Cartao> readAllCartao() {
        return cartaoRepository.findAll();
    }

    // readById JPA
    @GetMapping("/read/{numeroCartao}")
    public ResponseEntity<?> readByIdCartao(@PathVariable String numeroCartao) {
        Optional<Cartao> cartao = cartaoRepository.findById(numeroCartao);

        if (cartao.isPresent()) {
            return ResponseEntity.ok().body(cartao.get());
        }
        else {
            return ResponseEntity.badRequest().body("Cartao não encontrado pelo número: " + numeroCartao);
        }
    }

    // update JPA
    @PutMapping("/update/{numeroCartao}")
    public ResponseEntity<String> updateCartao(@PathVariable String numeroCartao, @RequestBody @Valid CartaoRequest request) {
        try {
            validationCartao.vencimentoValidate(request);

            Cartao cartao = cartaoRepository.getReferenceById(numeroCartao);

            CustomMapper.update(request, cartao);

            cartaoRepository.save(cartao);

            return ResponseEntity.ok().body("Cartão de Crédito atualizado com sucesso!");
        }
        catch (EntityNotFoundException | MappingException ex) {
            return ResponseEntity.badRequest().body("Cartão de Crédito não encontrado pelo número: " + numeroCartao);
        }
    }

    //delete JPA
    @DeleteMapping("/delete/{numeroCartao}") @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<String> deleteCartao(@PathVariable String numeroCartao){
        try {
            if (cartaoRepository.selectClientesCartao(numeroCartao) == null || cartaoRepository.selectPedidosCartao(numeroCartao) == null) {
                cartaoRepository.deleteById(numeroCartao);
                return ResponseEntity.ok().body("Cartão de Crédito deletado com sucesso!");
            }
            else {
                return ResponseEntity.badRequest().body("Cartão de Crédito associado a um Cliente ou Pedido.");
            }
        }
        catch (EmptyResultDataAccessException e){
            return ResponseEntity.badRequest().body("Cartão de Crédito não encontrado pelo numeroCartao: " + numeroCartao);
        }
    }
}
