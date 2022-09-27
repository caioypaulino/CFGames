package com.project.cfgames.controllers;

import com.project.cfgames.entities.Cartao;
import com.project.cfgames.entities.Cliente;
import com.project.cfgames.facade.Facade;
import com.project.cfgames.repository.CartaoRepository;
import com.project.cfgames.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@RestController
public class ClienteController {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    CartaoRepository cartaoRepository;

    Facade facade = new Facade();

    // create JPA
    @PostMapping("/cliente/save")
    public Cliente saveCliente(@RequestBody Cliente cliente) {
        return clienteRepository.save(facade.validaCliente(cliente));
    }

    // create with model
    @RequestMapping(value = "/cliente/form/save")
    public ModelAndView saveCliente(Cliente cliente, RedirectAttributes redirect) {
        if (facade.validaCliente(cliente) != cliente) {
            redirect.addFlashAttribute("mensagem", "teste");
            return new ModelAndView("redirect:/cliente/form/add");
        }

        clienteRepository.save(cliente);
        return new ModelAndView("redirect:/admin/painel");
    }

    // readAll JPA
    @GetMapping("/cliente")
    public List<Cliente> readAllCliente() {
        return clienteRepository.findAll();
    }

    // readById JPA
    @GetMapping("/cliente/{id}")
    public Cliente readByIdCliente(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if (cliente.isPresent()) {
            return cliente.get();
        } else {
            throw new RuntimeException("Cliente não encontrado pelo id: " + id);
        }
    }

    // add cartao
    @PutMapping("/cliente/{clienteId}/cartao/{numeroCartao}")
    public Cliente addCategoria(@PathVariable Long clienteId, @PathVariable Long numeroCartao) {
        Cliente cliente = clienteRepository.findById(clienteId).get();
        Cartao cartao = cartaoRepository.findById(numeroCartao).get();
        cliente.cartoesCliente(cartao);
        return clienteRepository.save(cliente);
    }

    // update JPA
    @PutMapping("/cliente")
    public Cliente updateCliente(@RequestBody Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    // delete JPA
    @GetMapping("/delete_cliente/{id}")
    public ModelAndView deleteCliente(@PathVariable Long id, RedirectAttributes redirect) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if (cliente.isPresent()) {
            clienteRepository.delete(cliente.get());
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
        Optional<Cliente> cliente = this.clienteRepository.findById(id);
        ModelAndView mv = new ModelAndView("updateCliente");
        mv.addObject("cliente", cliente);
        return mv;
    }
}
