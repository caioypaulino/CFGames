package com.project.cfgames.controllers;

import com.project.cfgames.clients.responses.EnderecoResponse;
import com.project.cfgames.dtos.mappers.CustomMapper;
import com.project.cfgames.dtos.requests.EnderecoRequest;
import com.project.cfgames.entities.Endereco;
import com.project.cfgames.repositories.EnderecoClienteRepository;
import com.project.cfgames.repositories.EnderecoRepository;
import com.project.cfgames.services.EnderecoService;
import com.project.cfgames.validations.ValidationEndereco;
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
@RequestMapping("/enderecos")
public class EnderecoController {
    @Autowired
    ValidationEndereco validationEndereco;
    @Autowired
    EnderecoService enderecoService;
    @Autowired
    EnderecoRepository enderecoRepository;
    @Autowired
    EnderecoClienteRepository enderecoClienteRepository;

    // create JPA API REST (CEP)
    @GetMapping("/cep/{cep}") @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<String> saveEndereco(@PathVariable String cep){
        validationEndereco.cepValidate(cep);

        EnderecoResponse enderecoResponse = enderecoService.buscarCep(cep);
        Endereco endereco = new Endereco(enderecoResponse, "Brasil");

        enderecoRepository.save(endereco);

        return ResponseEntity.ok().body("Endereço adicionado com sucesso!");
    }

    // create JPA
    @PostMapping("/save") @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<String> saveEndereco(@RequestBody @Valid Endereco endereco) {
        EnderecoResponse enderecoResponse = enderecoService.buscarCep(endereco.getCep());
        endereco = new Endereco(enderecoResponse, "Brasil");

        enderecoRepository.save(endereco);

        return ResponseEntity.ok().body("Endereço adicionado com sucesso!");
    }

    // readAll JPA
    @GetMapping("/read") @RolesAllowed("ROLE_ADMIN")
    public List<Endereco> readAllEndereco() {
        return enderecoRepository.findAll();
    }

    // readById JPA
    @GetMapping("/read/{cep}") @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<?> readByIdEndereco(@PathVariable String cep) {
        validationEndereco.cepValidate(cep);

        Optional<Endereco> endereco = enderecoRepository.findById(cep);

        if (endereco.isPresent()) {
            return ResponseEntity.ok().body(endereco.get());
        }
        else {
            return ResponseEntity.badRequest().body("Endereco não encontrado pelo CEP: " + cep);
        }
    }

    // update JPA
    @PutMapping("/update/{cep}") @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<String> updateEndereco(@PathVariable String cep, @RequestBody EnderecoRequest request) {
        try {
            validationEndereco.cepValidate(cep);

            Endereco endereco = enderecoRepository.getReferenceById(cep);

            CustomMapper.update(request, endereco);

            enderecoRepository.save(endereco);

            return ResponseEntity.ok().body("Endereço atualizado com sucesso");
        }
        catch (EntityNotFoundException | MappingException ex) {
            return ResponseEntity.badRequest().body("Endereço não encontrado pelo CEP: " + cep);
        }
    }

    // delete JPA
    @DeleteMapping("/delete/{cep}") @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<String> deleteEndereco(@PathVariable String cep) {
        try {
            validationEndereco.cepValidate(cep);

            if (enderecoClienteRepository.selectEnderecoCliente(cep).isEmpty()) {
                enderecoRepository.deleteById(cep);

                return ResponseEntity.ok().body("Endereço deletado com sucesso, CEP: " + cep);
            }
            else {
                return ResponseEntity.badRequest().body("Clientes atrelados a esse Endereço, CEP: " + cep);
            }
        }
        catch (EmptyResultDataAccessException ex) {
            return ResponseEntity.badRequest().body("Endereço não encontrado pelo CEP: " + cep);
        }
    }
}
