package com.project.cfgames.controllers;

import com.project.cfgames.clients.entities.Endereco;
import com.project.cfgames.clients.entities.relations.EnderecoCliente;
import com.project.cfgames.clients.responses.EnderecoResponse;
import com.project.cfgames.dtos.mappers.CustomMapper;
import com.project.cfgames.dtos.requests.EnderecoClienteRequest;
import com.project.cfgames.exceptions.CustomValidationException;
import com.project.cfgames.repositories.ClienteRepository;
import com.project.cfgames.repositories.EnderecoClienteRepository;
import com.project.cfgames.repositories.EnderecoRepository;
import com.project.cfgames.services.EnderecoService;
import com.project.cfgames.validations.ValidationEnderecoCliente;
import com.project.cfgames.validations.handlers.HandlerCustomValidationsExceptions;
import com.project.cfgames.validations.handlers.HandlerValidationsExceptions;
import feign.FeignException;
import org.modelmapper.MappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/enderecosclientes")
public class EnderecoClienteController {

    @Autowired
    EnderecoClienteRepository enderecoClienteRepository;
    @Autowired
    EnderecoRepository enderecoRepository;
    @Autowired
    EnderecoService enderecoService;
    @Autowired
    ValidationEnderecoCliente validationEnderecoCliente;
    @Autowired
    ClienteRepository clienteRepository;

    // create JPA
    @PostMapping("/form/save")
    public ResponseEntity<?> saveEnderecoCliente(@RequestBody @Valid EnderecoCliente enderecoCliente) {
        validationEnderecoCliente.allValidates(enderecoCliente);

        try {
            if (enderecoRepository.findById(enderecoCliente.getEndereco().getCep()).isEmpty()) {
                EnderecoResponse enderecoResponse = enderecoService.buscarCep(enderecoCliente.getEndereco().getCep());

                String pais = "Brasil";
                Endereco endereco = new Endereco(enderecoResponse, pais);
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
            return ResponseEntity.badRequest().body("EnderecoCliente não encontrado pelo id: " + id);
        }
    }

    // update JPA
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateEnderecoCliente(@PathVariable Long id, @RequestBody @Valid EnderecoClienteRequest request) {

        try {
            EnderecoCliente enderecoCliente = enderecoClienteRepository.getReferenceById(id);

            if (request.getEndereco() != null && enderecoRepository.findById(request.getEndereco().getCep()).isEmpty()) {
                EnderecoResponse enderecoResponse = enderecoService.buscarCep(request.getEndereco().getCep());

                String pais = "Brasil";
                Endereco endereco = new Endereco(enderecoResponse, pais);
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
    public ResponseEntity<String> deleteCliente(@PathVariable Long id) {
        Optional<EnderecoCliente> enderecoCliente = enderecoClienteRepository.findById(id);

        if (enderecoCliente.isPresent()) {
            enderecoClienteRepository.delete(enderecoCliente.get());
            return ResponseEntity.ok().body("EnderecoCliente deletado com sucesso, id: " + id);
        }
        else {
            return ResponseEntity.badRequest().body("EnderecoCliente não encontrado pelo id: " + id);
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
        return "Tipo inválido(Ex: 0-ENTREGA, 1-COBRANCA, 2-AMBOS).";
    }

    @GetMapping("/form/add")
    public ModelAndView getFormadd() {
        ModelAndView mv = new ModelAndView("cadastroCliente");
        return mv;
    }

}
