package com.project.cfgames.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.cfgames.dao.ClienteDao;
import com.project.cfgames.entities.Cliente;
import com.project.cfgames.helper.StrategyCliente;

@RestController
public class ClienteController {

    @Autowired
    ClienteDao clienteDao;

    // create JPA
    @PostMapping("/cliente/save")
    public Cliente saveCliente(@RequestBody Cliente cliente) {
        StrategyCliente StrategyCliente = new StrategyCliente();
        if(!StrategyCliente.allValidates(cliente)){
            
        }
        return clienteDao.save(cliente);
    }

    // create with model
    @RequestMapping(value = "/cliente/form/save")
    public String saveCliente(Cliente cliente, RedirectAttributes redirect){
        StrategyCliente StrategyCliente = new StrategyCliente();
        if(!StrategyCliente.allValidates(cliente)){
            redirect.addFlashAttribute("mensagem", "teste");
            return "redirect:/cliente/form/add";
        }
        System.out.println(cliente);
        clienteDao.save(cliente);
        return "redirect:/cliente/form/add";
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
