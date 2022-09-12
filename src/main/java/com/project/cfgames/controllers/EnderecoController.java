package com.project.cfgames.controllers;

import java.util.List;
import java.util.Optional;

import com.project.cfgames.dao.EnderecoDao;
import com.project.cfgames.entities.Endereco;
import com.project.cfgames.facade.Facade;

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
public class EnderecoController {

    @Autowired
    EnderecoDao enderecoDao;

    Facade facade = new Facade();

    // create JPA
    @PostMapping("/endereco/form/save")
    public Endereco saveEnderecoCliente(@RequestBody Endereco endereco) {
        return enderecoDao.save(facade.validaEndereco(endereco));
    }

    // readAll JPA
    @GetMapping("/endereco")
    public List<Endereco> readAllEnderecoCliente() {
        return enderecoDao.findAll();
    }

    // readById JPA
    @GetMapping("/endereco/{id}")
    public Endereco readByIdEnderecoCliente(@PathVariable Long id) {
        Optional<Endereco> endereco = enderecoDao.findById(id);
        if (endereco.isPresent()) {
            return endereco.get();
        } else {
            throw new RuntimeException("Endereco não encontrado pelo id: " + id);
        }
    }

    // update JPA
    @PutMapping("/endereco")
    public Endereco updateEnderecoCliente(@RequestBody Endereco endereco) {
        return enderecoDao.save(endereco);
    }

    // delete JPA
    @DeleteMapping("/endereco/{id}")
    public String deleteCliente(@PathVariable Long id) {
        Optional<Endereco> endereco = enderecoDao.findById(id);
        if (endereco.isPresent()) {
            enderecoDao.delete(endereco.get());
            return "Endereco deletado com sucesso, id: " + id;
        } else {
            throw new RuntimeException("Endereco não encontrado pelo id: " + id);
        }
    }

    @GetMapping("/endereco/form/add")
    public ModelAndView getFormadd() {
        ModelAndView mv = new ModelAndView("cadastro");
        return mv;

    }

}
