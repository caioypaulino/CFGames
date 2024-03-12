package com.project.cfgames.controllers.admin;

import com.project.cfgames.dtos.mappers.CustomMapper;
import com.project.cfgames.dtos.responses.EnderecoClienteResponse;
import com.project.cfgames.entities.relations.EnderecoCliente;
import com.project.cfgames.repositories.EnderecoClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminEnderecoClienteController {
    @Autowired
    EnderecoClienteRepository enderecoClienteRepository;

    // enderecos clientes - readAll
    @GetMapping("/enderecosclientes") @RolesAllowed("ROLE_ADMIN")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<?> readAllEnderecosCliente() {
        List<EnderecoClienteResponse> enderecos = new ArrayList<>();

        for (EnderecoCliente enderecoCliente : enderecoClienteRepository.findAll()) {
            EnderecoClienteResponse endereco = new EnderecoClienteResponse();

            CustomMapper.update(enderecoCliente, endereco);

            enderecos.add(endereco);
        }

        return ResponseEntity.ok().body(enderecos);
    }

    // enderecos clientes - readById
    @GetMapping("/enderecosclientes/buscar/{id}") @RolesAllowed("ROLE_ADMIN")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<?> readByIdEnderecoCliente(@PathVariable Long id) {
        Optional<EnderecoCliente> enderecoCliente = enderecoClienteRepository.findById(id);

        if (enderecoCliente.isPresent()) {
            EnderecoClienteResponse enderecoClienteResponse = new EnderecoClienteResponse();

            CustomMapper.update(enderecoCliente.get(), enderecoClienteResponse);

            return ResponseEntity.ok().body(enderecoClienteResponse);
        }
        else {
            return ResponseEntity.badRequest().body("Endereço Cliente não encontrado pelo id: " + id);
        }
    }
}
