package com.project.cfgames.controllers;

import java.util.List;
import java.util.Optional;

import com.project.cfgames.repositories.EnderecoClienteRepository;
import com.project.cfgames.entities.relations.EnderecoCliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/enderecosclientes")
public class EnderecoClienteController {

    @Autowired
    EnderecoClienteRepository enderecoClienteRepository;

    // create JPA
    @PostMapping("/form/save")
    public EnderecoCliente saveEnderecoCliente(@RequestBody EnderecoCliente enderecoCliente) {
        return enderecoClienteRepository.save(enderecoCliente);
    }

    // readAll JPA
    @GetMapping("/read")
    public List<EnderecoCliente> readAllEnderecoCliente() {
        return enderecoClienteRepository.findAll();
    }

    // readById JPA
    @GetMapping("/read/{id}")
    public EnderecoCliente readByIdEnderecoCliente(@PathVariable Long id) {
        Optional<EnderecoCliente> enderecoCliente = enderecoClienteRepository.findById(id);
        if (enderecoCliente.isPresent()) {
            return enderecoCliente.get();
        } else {
            throw new RuntimeException("EnderecoCliente não encontrado pelo id: " + id);
        }
    }

    // update JPA
    @PutMapping("/update")
    public EnderecoCliente updateEnderecoCliente(@RequestBody EnderecoCliente enderecoCliente) {
        return enderecoClienteRepository.save(enderecoCliente);
    }

    // delete JPA
    @DeleteMapping("/delete/{id}")
    public String deleteCliente(@PathVariable Long id) {
        Optional<EnderecoCliente> enderecoCliente = enderecoClienteRepository.findById(id);
        if (enderecoCliente.isPresent()) {
            enderecoClienteRepository.delete(enderecoCliente.get());
            return "EnderecoCliente deletado com sucesso, id: " + id;
        } else {
            throw new RuntimeException("EnderecoCliente não encontrado pelo id: " + id);
        }
    }

    @GetMapping("/form/add")
    public ModelAndView getFormadd() {
        ModelAndView mv = new ModelAndView("cadastroCliente");
        return mv;
    }

}
