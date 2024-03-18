package com.project.cfgames.controllers;

import com.project.cfgames.clients.responses.EnderecoResponse;
import com.project.cfgames.entities.Cliente;
import com.project.cfgames.entities.Endereco;
import com.project.cfgames.entities.relations.EnderecoCliente;
import com.project.cfgames.repositories.ClienteRepository;
import com.project.cfgames.repositories.EnderecoClienteRepository;
import com.project.cfgames.repositories.EnderecoRepository;
import com.project.cfgames.services.ClienteService;
import com.project.cfgames.services.EnderecoService;
import com.project.cfgames.validations.ValidationCliente;
import com.project.cfgames.validations.ValidationEnderecoCliente;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/cadastro")
public class CadastroController {
    @Autowired
    ValidationCliente validationCliente;
    @Autowired
    ClienteService clienteService;
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    ValidationEnderecoCliente validationEnderecoCliente;
    @Autowired
    EnderecoClienteRepository enderecoClienteRepository;
    @Autowired
    EnderecoService enderecoService;
    @Autowired
    EnderecoRepository enderecoRepository;

    // create JPA
    @PostMapping("/cliente")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<String> cadastroCliente(@RequestBody @Valid Cliente cliente) {
        validationCliente.allValidates(cliente);

        cliente.setSenha(clienteService.bCryptSenha(cliente));

        clienteRepository.save(cliente);

        return ResponseEntity.ok().body("Cliente adicionado com sucesso!");
    }

    // create JPA
    @PostMapping("/endereco")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<?> cadastroEnderecoCliente(@RequestBody @Valid EnderecoCliente enderecoCliente) {
        try {
            validationEnderecoCliente.allValidates(enderecoCliente);

            if (enderecoRepository.findById(enderecoCliente.getEndereco().getCep()).isEmpty()) {
                EnderecoResponse enderecoResponse = enderecoService.buscarCep(enderecoCliente.getEndereco().getCep());
                Endereco endereco = new Endereco(enderecoResponse, "Brasil");

                enderecoRepository.save(endereco);
            }

            enderecoClienteRepository.save(enderecoCliente);

            return ResponseEntity.ok().body("Endereço Cliente adicionado com sucesso!");
        }
        catch (FeignException ex) {
            return ResponseEntity.badRequest().body("Cep inválido.");
        }
        catch (InvalidDataAccessApiUsageException ex) {
            return ResponseEntity.badRequest().body("Cliente ou Cep null.");
        }
    }
}
