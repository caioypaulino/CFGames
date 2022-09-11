package com.project.cfgames.controller;

import java.util.List;
import java.util.Optional;

import com.project.cfgames.dao.ClienteDao;
import com.project.cfgames.entities.Cliente;
import com.project.cfgames.helper.HelperCliente;

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
public class ClienteController {

    @Autowired
    ClienteDao clienteDao;

    // create JPA
    @PostMapping("/cliente/form/save")
    public Cliente saveCliente(@RequestBody Cliente cliente) {
        HelperCliente helperCliente = new HelperCliente();

        if(!helperCliente.allValidates(cliente.getNome())){
            throw new RuntimeException("Algum dado incorreto");
        }
        return clienteDao.save(cliente);
    }

    // readAll JPA
    @GetMapping("/cliente")
    public List<Cliente> readAllCliente() {
        return clienteDao.findAll();
    }

    // readById JPA
    @GetMapping("/cliente/{id}")
    public Cliente readByIdCliente(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteDao.findById(id);
        if (cliente.isPresent()) {
            return cliente.get();
        } else {
            throw new RuntimeException("Cliente não encontrado pelo id: " + id);
        }
    }

    // update JPA
    @PutMapping("/cliente")
    public Cliente updateCliente(@RequestBody Cliente cliente) {
        return clienteDao.save(cliente);
    }

    // delete JPA
    @DeleteMapping("/cliente/{id}")
    public String deleteCliente(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteDao.findById(id);
        if (cliente.isPresent()) {
            clienteDao.delete(cliente.get());
            return "Cliente deletado com sucesso, id: " + id;
        } else {
            throw new RuntimeException("Cliente não encontrado pelo id: " + id);
        }
    }

    @GetMapping("/cliente/form/add")
    public ModelAndView getFormadd() {
        ModelAndView mv = new ModelAndView("cadastro");
        return mv;

    }

}
