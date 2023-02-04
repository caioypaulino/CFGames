package com.project.cfgames.controllers;

import com.project.cfgames.clients.responses.EnderecoResponse;
import com.project.cfgames.entities.Endereco;
import com.project.cfgames.repositories.EnderecoRepository;
import com.project.cfgames.services.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    @Autowired
    EnderecoRepository enderecoRepository;

    @Autowired
    EnderecoService enderecoService;

    // create JPA API REST (CEP)
    @GetMapping("/cep/{cep}")
    public Endereco verificarEndereco(@PathVariable String cep){
        EnderecoResponse enderecoResponse = enderecoService.buscarCep(cep);
        
        String pais = "Brasil";
        Endereco endereco = new Endereco(enderecoResponse, pais);

        return enderecoRepository.save(endereco);
    }

    // create JPA
    @PostMapping("/form/save")
    public Endereco saveEnderecoCliente(@RequestBody Endereco endereco) {
        return enderecoRepository.save(endereco);
    }

    // readAll JPA
    @GetMapping("/read")
    public List<Endereco> readAllEnderecoCliente() {
        return enderecoRepository.findAll();
    }

    // readById JPA
    @GetMapping("/read/{cep}")
    public Endereco readByIdEnderecoCliente(@PathVariable String cep) {
        Optional<Endereco> endereco = enderecoRepository.findById(cep);
        if (endereco.isPresent()) {
            return endereco.get();
        } else {
            throw new RuntimeException("Endereco não encontrado pelo cep: " + cep);
        }
    }

    // update JPA
    @PutMapping("/update")
    public Endereco updateEnderecoCliente(@RequestBody Endereco endereco) {
        return enderecoRepository.save(endereco);
    }

    // delete JPA
    @DeleteMapping("/delete/{cep}")
    public String deleteCliente(@PathVariable String cep) {
        Optional<Endereco> endereco = enderecoRepository.findById(cep);
        if (endereco.isPresent()) {
            enderecoRepository.delete(endereco.get());
            return "Endereco deletado com sucesso, cep: " + cep;
        } else {
            throw new RuntimeException("Endereco não encontrado pelo cep: " + cep);
        }
    }

    @GetMapping("/form/add")
    public ModelAndView getFormadd() {
        ModelAndView mv = new ModelAndView("cadastroCliente");
        return mv;
    }

}
