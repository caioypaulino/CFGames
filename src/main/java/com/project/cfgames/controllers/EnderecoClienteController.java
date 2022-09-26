package com.project.cfgames.controllers;

import java.util.List;
import java.util.Optional;

import com.project.cfgames.repository.EnderecoClienteRepository;
import com.project.cfgames.entities.relations.EnderecoCliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class EnderecoClienteController {

    @Autowired
    EnderecoClienteRepository enderecoClienteRepository;

    // create JPA
    @PostMapping("/enderecocliente/form/save")
    public EnderecoCliente saveEnderecoCliente(@RequestBody EnderecoCliente enderecoCliente) {
        return enderecoClienteRepository.save(enderecoCliente);
    }

    // readAll JPA
    @GetMapping("/enderecocliente")
    public List<EnderecoCliente> readAllEnderecoCliente() {
        return enderecoClienteRepository.findAll();
    }

    // readById JPA
    @GetMapping("/enderecocliente/{id}")
    public EnderecoCliente readByIdEnderecoCliente(@PathVariable Long id) {
        Optional<EnderecoCliente> enderecoCliente = enderecoClienteRepository.findById(id);
        if (enderecoCliente.isPresent()) {
            return enderecoCliente.get();
        } else {
            throw new RuntimeException("EnderecoCliente não encontrado pelo id: " + id);
        }
    }

    // update JPA
    @PutMapping("/enderecocliente")
    public EnderecoCliente updateEnderecoCliente(@RequestBody EnderecoCliente enderecoCliente) {
        return enderecoClienteRepository.save(enderecoCliente);
    }

    // delete JPA
    @DeleteMapping("/enderecocliente/{id}")
    public String deleteCliente(@PathVariable Long id) {
        Optional<EnderecoCliente> enderecoCliente = enderecoClienteRepository.findById(id);
        if (enderecoCliente.isPresent()) {
            enderecoClienteRepository.delete(enderecoCliente.get());
            return "EnderecoCliente deletado com sucesso, id: " + id;
        } else {
            throw new RuntimeException("EnderecoCliente não encontrado pelo id: " + id);
        }
    }

    @GetMapping("/enderecocliente/form/add")
    public ModelAndView getFormadd() {
        ModelAndView mv = new ModelAndView("cadastroCliente");
        return mv;

    }

}