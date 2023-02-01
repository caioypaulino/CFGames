package com.project.cfgames.controllers;

import com.project.cfgames.entities.Cartao;
import com.project.cfgames.entities.Cliente;
import com.project.cfgames.facades.Facade;
import com.project.cfgames.repositories.CartaoRepository;
import com.project.cfgames.repositories.ClienteRepository;
import com.project.cfgames.responses.ClienteResponse;
import com.project.cfgames.services.CartaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    CartaoRepository cartaoRepository;

    @Autowired
    CartaoService cartaoService;

    Facade facade = new Facade();

    // create JPA
    @PostMapping("/save")
    public ResponseEntity<ClienteResponse> saveCliente(@RequestBody Cliente cliente) {
        var clienteSalvo = clienteRepository.save(facade.validaCliente(cliente));
        var clienteResponse = clienteSalvo.toResponse();
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteResponse);
    }

    // create with model
    @RequestMapping(value = "/form/save")
    public ModelAndView saveCliente(Cliente cliente, RedirectAttributes redirect) {
        if (facade.validaCliente(cliente) != cliente) {
            redirect.addFlashAttribute("mensagem", "teste");
            return new ModelAndView("redirect:/cliente/form/add");
        }

        clienteRepository.save(cliente);
        return new ModelAndView("redirect:/admin/painel");
    }

    // readAll JPA
    @GetMapping("/read")
    public List<Cliente> readAllCliente() {
        return clienteRepository.findAll();
    }

    // readById JPA
    @GetMapping("/read/{id}")
    public Cliente readByIdCliente(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if (cliente.isPresent()) {
            return cliente.get();
        }
        else {
            throw new RuntimeException("Cliente não encontrado pelo id: " + id);
        }
    }

    // add Cartao
    @PutMapping("/{id}/cartao")
    public ResponseEntity<String> addCartao(@PathVariable Long id, @RequestBody Cartao cartao) {
        try {
            Cliente cliente = clienteRepository.getReferenceById(id);

            if (cartaoRepository.selectCartao(cartao.getNumeroCartao()) == null) {
                cartao.setBandeira(cartaoService.bandeiraCartao(cartao));
                cartaoRepository.save(cartao);
            }
            Cartao cartaoR = cartaoRepository.getReferenceById(cartao.getNumeroCartao());

            cliente.cartoesCliente(cartaoR);
            clienteRepository.save(cliente);

            return ResponseEntity.ok().body("Cartão adicionado com sucesso ao Cliente Id: " + id);
        }
        catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body("Cliente não encontrado pelo Cliente Id: " + id);
        }
    }

    // remove Cartao
    @DeleteMapping("/{id}/cartao")
    public ResponseEntity<String> removeCartao(@PathVariable Long id, @RequestBody Cartao cartao) {
        if (cartaoRepository.selectCartaoCliente(id, cartao.getNumeroCartao()) != null) {

            cartaoRepository.removeCartao(id, cartao.getNumeroCartao());

            return ResponseEntity.ok("Cartão removido com sucesso!");
        }
        else {
            return ResponseEntity.badRequest().body("Cliente ou Cartão não encontrados pelo clienteId " + id + " e numeroCartao " + cartao.getNumeroCartao());
        }
    }

    // update JPA
    @PutMapping("/update")
    public Cliente updateCliente(@RequestBody Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    // delete JPA
    @DeleteMapping("/delete_cliente/{id}")
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

    @GetMapping("/form/add")
    public ModelAndView getFormadd() {
        return new ModelAndView("cadastroCliente");
    }

    @GetMapping("/form/update/{id}")
    public ModelAndView getFormUpdate(@PathVariable("id") Long id){
        Optional<Cliente> cliente = this.clienteRepository.findById(id);
        ModelAndView mv = new ModelAndView("updateCliente");
        mv.addObject("cliente", cliente);
        return mv;
    }
}
