package com.project.cfgames.controllers.admin;

import com.project.cfgames.clients.responses.EnderecoResponse;
import com.project.cfgames.dtos.mappers.CustomMapper;
import com.project.cfgames.dtos.requests.EnderecoRequest;
import com.project.cfgames.entities.Endereco;
import com.project.cfgames.repositories.EnderecoClienteRepository;
import com.project.cfgames.repositories.EnderecoRepository;
import com.project.cfgames.services.EnderecoService;
import com.project.cfgames.validations.ValidationEndereco;
import feign.FeignException;
import org.modelmapper.MappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminEnderecoController {
    @Autowired
    EnderecoService enderecoService;
    @Autowired
    ValidationEndereco validationEndereco;
    @Autowired
    EnderecoRepository enderecoRepository;
    @Autowired
    EnderecoClienteRepository enderecoClienteRepository;

    // enderecos - create
    @PostMapping("/enderecos/add") @RolesAllowed("ROLE_ADMIN")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<String> addEndereco(@RequestBody @Valid Endereco endereco) {
        try {
            EnderecoResponse enderecoResponse = enderecoService.buscarCep(endereco.getCep());
            endereco = new Endereco(enderecoResponse, "Brasil");

            enderecoRepository.save(endereco);

            return ResponseEntity.ok().body("Endereço adicionado com sucesso!");
        }
        catch (FeignException ex) {
            return ResponseEntity.badRequest().body("Cep inválido.");
        }
        catch (InvalidDataAccessApiUsageException ex) {
            return ResponseEntity.badRequest().body("Cliente ou Cep null.");
        }
    }

    // enderecos - readAll
    @GetMapping("/enderecos") @RolesAllowed("ROLE_ADMIN")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<?> readAllEnderecos() {
        return ResponseEntity.ok(enderecoRepository.findAll());
    }

    // enderecos - readByCep
    @GetMapping("/enderecos/buscar/{cep}") @RolesAllowed("ROLE_ADMIN")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<?> readByCepEndereco(@PathVariable String cep) {
        validationEndereco.cepValidate(cep);

        Optional<Endereco> endereco = enderecoRepository.findById(cep);

        if (endereco.isPresent()) {
            return ResponseEntity.ok().body(endereco.get());
        }
        else {
            return ResponseEntity.badRequest().body("Endereco não encontrado pelo CEP: " + cep);
        }
    }

    // enderecos - update
    @PutMapping("/enderecos/update/{cep}") @RolesAllowed("ROLE_ADMIN")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
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

    // enderecos - delete
    @DeleteMapping("/enderecos/delete/{cep}") @RolesAllowed("ROLE_ADMIN")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
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
