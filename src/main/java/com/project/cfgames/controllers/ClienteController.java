package com.project.cfgames.controllers;

import java.util.List;
import java.util.Optional;

import com.project.cfgames.dao.ClienteDao;
import com.project.cfgames.entities.Cliente;
import com.project.cfgames.facade.Facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
public class ClienteController {

    @Autowired
    ClienteDao clienteDao;
    
    Facade facade = new Facade();

    // create JPA
    @PostMapping("/cliente/save")
    public Cliente saveCliente(@RequestBody Cliente cliente) {
        return clienteDao.save(facade.validaCliente(cliente));
    }

    // create with model
    @RequestMapping(value = "/cliente/form/save")
    public ModelAndView saveCliente(Cliente cliente, RedirectAttributes redirect) {
        if (facade.validaCliente(cliente) != cliente) {
            redirect.addFlashAttribute("mensagem", "teste");
            return new ModelAndView("redirect:/cliente/form/add");
        }

        clienteDao.save(cliente);
        return new ModelAndView("redirect:/admin/painel");
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
    @GetMapping("/delete_cliente/{id}")
    public ModelAndView deleteCliente(@PathVariable Long id, RedirectAttributes redirect) {
        Optional<Cliente> cliente = clienteDao.findById(id);
        if (cliente.isPresent()) {
            clienteDao.delete(cliente.get());
            redirect.addFlashAttribute("mensagem", "Cliente deletado!");
            return new ModelAndView("redirect:/admin/painel");
        } else {
            throw new RuntimeException("Cliente não encontrado pelo id: " + id);
        }
    }

    @GetMapping("/cliente/form/add")
    public ModelAndView getFormadd() {
        return new ModelAndView("cadastroCliente");
    }

    @GetMapping("/cliente/form/update/{id}")
    public ModelAndView getFormUpdate(@PathVariable("id") Long id){
        Optional<Cliente> cliente = this.clienteDao.findById(id);
        ModelAndView mv = new ModelAndView("updateCliente");
        mv.addObject("cliente", cliente);
        return mv;
    }
}
