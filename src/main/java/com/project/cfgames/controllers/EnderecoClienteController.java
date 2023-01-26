package com.project.cfgames.controllers;

import com.project.cfgames.clients.responses.EnderecoResponse;
import com.project.cfgames.entities.Endereco;
import com.project.cfgames.entities.relations.EnderecoCliente;
import com.project.cfgames.repositories.EnderecoClienteRepository;
import com.project.cfgames.repositories.EnderecoRepository;
import com.project.cfgames.services.EnderecoService;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
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

    // create JPA
    @PostMapping("/form/save")
    public ResponseEntity<String> saveEnderecoCliente(@RequestBody EnderecoCliente enderecoCliente) {
        try {
            if (enderecoRepository.selectEnderecoCep(enderecoCliente.getEndereco().getCep()) == null) {
                EnderecoResponse enderecoResponse = enderecoService.buscarCep(enderecoCliente.getEndereco().getCep());

                String pais = "Brasil";
                Endereco endereco = new Endereco(enderecoResponse, pais);
                enderecoRepository.save(endereco);
            }

            enderecoClienteRepository.save(enderecoCliente);
            return ResponseEntity.ok().body("Endereço Cliente adicionado com sucesso!");
        }
        catch (FeignException e) {
            return ResponseEntity.badRequest().body("Cep inválido.");
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
    @PutMapping("/update")
    public ResponseEntity<String> updateEnderecoCliente(@RequestBody EnderecoCliente enderecoCliente) {
        enderecoClienteRepository.save(enderecoCliente);
        return ResponseEntity.ok().body("Endereço Cliente atualizado com sucesso!");
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

    @GetMapping("/form/add")
    public ModelAndView getFormadd() {
        ModelAndView mv = new ModelAndView("cadastroCliente");
        return mv;
    }

}
