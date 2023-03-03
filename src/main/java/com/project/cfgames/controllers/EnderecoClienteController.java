package com.project.cfgames.controllers;

import com.project.cfgames.clients.responses.EnderecoResponse;
import com.project.cfgames.dtos.mappers.CustomMapper;
import com.project.cfgames.dtos.requests.EnderecoClienteRequest;
import com.project.cfgames.entities.Endereco;
import com.project.cfgames.entities.relations.EnderecoCliente;
import com.project.cfgames.repositories.EnderecoClienteRepository;
import com.project.cfgames.repositories.EnderecoRepository;
import com.project.cfgames.services.EnderecoService;
import com.project.cfgames.validations.ValidationEnderecoCliente;
import com.project.cfgames.validations.exceptions.CustomValidationException;
import com.project.cfgames.validations.handlers.HandlerCustomValidationsExceptions;
import com.project.cfgames.validations.handlers.HandlerValidationsExceptions;
import feign.FeignException;
import org.modelmapper.MappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/enderecosclientes")
public class EnderecoClienteController {
    @Autowired
    ValidationEnderecoCliente validationEnderecoCliente;
    @Autowired
    EnderecoClienteRepository enderecoClienteRepository;
    @Autowired
    EnderecoService enderecoService;
    @Autowired
    EnderecoRepository enderecoRepository;

    // create JPA
    @PostMapping("/form/save")
    public ResponseEntity<?> saveEnderecoCliente(@RequestBody @Valid EnderecoCliente enderecoCliente) {
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

    // readAll JPA
    @GetMapping("/read")
    public List<EnderecoCliente> readAllEnderecoCliente() {
        return enderecoClienteRepository.findAll();
    }

    // readById JPA
    @GetMapping("/read/{id}")
    public ResponseEntity<?> readByIdEnderecoCliente(@PathVariable Long id) {
        Optional<EnderecoCliente> enderecoCliente = enderecoClienteRepository.findById(id);

        if (enderecoCliente.isPresent()) {
            return ResponseEntity.ok().body(enderecoCliente.get());
        }
        else {
            return ResponseEntity.badRequest().body("Endereço Cliente não encontrado pelo id: " + id);
        }
    }

    // update JPA
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateEnderecoCliente(@PathVariable Long id, @RequestBody @Valid EnderecoClienteRequest request) {
        try {
            EnderecoCliente enderecoCliente = enderecoClienteRepository.getReferenceById(id);

            if (request.getEndereco() != null && enderecoRepository.findById(request.getEndereco().getCep()).isEmpty()) {
                EnderecoResponse enderecoResponse = enderecoService.buscarCep(request.getEndereco().getCep());
                Endereco endereco = new Endereco(enderecoResponse, "Brasil");

                enderecoRepository.save(endereco);
            }

            CustomMapper.update(request, enderecoCliente);

            enderecoClienteRepository.save(enderecoCliente);

            return ResponseEntity.ok().body("Endereço Cliente atualizado com sucesso!");
        }
        catch (FeignException ex ) {
            return ResponseEntity.badRequest().body("Cep inválido.");
        }
        catch (EntityNotFoundException | MappingException ex) {
            return ResponseEntity.badRequest().body("Endereço Cliente não encontrado pelo id: " + id);
        }
    }

    // delete JPA
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEnderecoCliente(@PathVariable Long id) {
        try {
            enderecoClienteRepository.deleteById(id);

            return ResponseEntity.ok().body("Endereço Cliente deletado com sucesso, id: " + id);
        }
        catch (EmptyResultDataAccessException ex) {
            return ResponseEntity.badRequest().body("Endereço Cliente não encontrado pelo id: " + id);
        }
    }

    // handler @validation exception
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationsExceptions(MethodArgumentNotValidException exception) {
        return HandlerValidationsExceptions.handler(exception);
    }

    // handler custom validation exception
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CustomValidationException.class)
    public String handleCustomValidationsExceptions (CustomValidationException exception){
        return HandlerCustomValidationsExceptions.handler(exception);
    }

    // handler Enum type Json exception
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public String handleCustomValidationsExceptions (){
        return "Tipo inválido(Ex:\n0- ENTREGA,\n1- COBRANCA,\n2- AMBOS).";
    }
}
