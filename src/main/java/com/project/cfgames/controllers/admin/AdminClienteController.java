package com.project.cfgames.controllers.admin;

import com.project.cfgames.dtos.mappers.CustomMapper;
import com.project.cfgames.dtos.requests.EmailRequest;
import com.project.cfgames.dtos.requests.NomeRequest;
import com.project.cfgames.dtos.responses.ClienteResponse;
import com.project.cfgames.entities.Cliente;
import com.project.cfgames.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/admin")
public class AdminClienteController {
    @Autowired
    ClienteRepository clienteRepository;

    // clientes - readAll
    @GetMapping("/clientes") @RolesAllowed("ROLE_ADMIN")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<?> readAllClientes() {
        List<Cliente> clientes =  clienteRepository.findAll();

        List<ClienteResponse> clientesResponse = new ArrayList<>();

        for (Cliente cliente : clientes) {
            ClienteResponse clienteResponse = new ClienteResponse();

            CustomMapper.update(cliente, clienteResponse);

            clientesResponse.add(clienteResponse);
        }

        return ResponseEntity.ok().body(clientesResponse);
    }

    // clientes - readById
    @GetMapping("/clientes/buscar/{id}") @RolesAllowed("ROLE_ADMIN")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<?> readByIdCliente(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);

        if (cliente.isPresent()) {
            ClienteResponse clienteResponse = new ClienteResponse();

            CustomMapper.update(cliente.get(), clienteResponse);

            return ResponseEntity.ok().body(clienteResponse);
        }
        else {
            return ResponseEntity.badRequest().body("Cliente não encontrado pelo id: " + id);
        }
    }

    // clientes - readByNome
    @GetMapping("/clientes/buscar/nome") @RolesAllowed("ROLE_ADMIN")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<?> readByNomeClientes(@RequestBody @Valid NomeRequest request) {
        Optional<List<Cliente>> clientes = clienteRepository.findByNome(request.getNome());

        if (clientes.isPresent() && !clientes.get().isEmpty()) {
            List<ClienteResponse> clientesResponse = new ArrayList<>();

            for (Cliente cliente : clientes.get()) {
                ClienteResponse clienteResponse = new ClienteResponse();

                CustomMapper.update(cliente, clienteResponse);

                clientesResponse.add(clienteResponse);
            }

            return ResponseEntity.ok().body(clientesResponse);
        }
        else {
            return ResponseEntity.badRequest().body("Clientes não encontrados pelo nome: " + request.getNome());
        }
    }

    // clientes - readByEmail
    @GetMapping("/clientes/buscar/email") @RolesAllowed("ROLE_ADMIN")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<?> readByEmailCliente(@RequestBody @Valid EmailRequest request) {
        Optional<Cliente> cliente = Optional.ofNullable(clienteRepository.findByEmail(request.getEmail()));

        if (cliente.isPresent()) {
            ClienteResponse clienteResponse = new ClienteResponse();

            CustomMapper.update(cliente.get(), clienteResponse);

            return ResponseEntity.ok().body(clienteResponse);
        }
        else {
            return ResponseEntity.badRequest().body("Cliente não encontrado pelo e-mail: " + request.getEmail());
        }
    }

    // clientes - delete
    @DeleteMapping("/clientes/delete/{id}") @RolesAllowed("ROLE_ADMIN")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<String> deleteCliente(@PathVariable Long id){
        try {
            clienteRepository.deleteById(id);

            return ResponseEntity.ok().body("Cliente deletado com sucesso!");
        }
        catch (EmptyResultDataAccessException ex) {
            return ResponseEntity.badRequest().body("Cliente não encontrado pelo Cliente Id: " + id);
        }
    }
}
