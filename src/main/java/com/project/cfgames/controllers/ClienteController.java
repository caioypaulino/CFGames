package com.project.cfgames.controllers;

import com.project.cfgames.entities.Cartao;
import com.project.cfgames.entities.Cliente;
import com.project.cfgames.validations.exceptions.CustomValidationException;
import com.project.cfgames.repositories.CartaoRepository;
import com.project.cfgames.repositories.ClienteRepository;
import com.project.cfgames.services.CartaoService;
import com.project.cfgames.validations.ValidationCartao;
import com.project.cfgames.validations.ValidationCliente;
import com.project.cfgames.validations.handlers.HandlerCustomValidationsExceptions;
import com.project.cfgames.validations.handlers.HandlerValidationsExceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
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

    @Autowired
    ValidationCliente validationCliente;

    @Autowired
    ValidationCartao validationCartao;

    // create JPA
    @PostMapping("/save")
    public ResponseEntity<String> saveCliente(@RequestBody @Valid Cliente cliente) {

        validationCliente.allValidates(cliente);

        clienteRepository.save(cliente);

        return ResponseEntity.ok().body("Cliente adicionado com sucesso!");
    }

    // readAll JPA
    @GetMapping("/read")
    public List<Cliente> readAllCliente() {
        return clienteRepository.findAll();
    }

    // readById JPA
    @GetMapping("/read/{id}")
    public ResponseEntity<?> readByIdCliente(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);

        if (cliente.isPresent()) {
            return ResponseEntity.ok().body(cliente.get());
        }
        else {
            return ResponseEntity.badRequest().body("Cliente não encontrado pelo id: " + id);
        }
    }

    // add Cartao
    @PutMapping("/{id}/cartao")
    public ResponseEntity<String> addCartao(@PathVariable Long id, @RequestBody @Valid Cartao cartao) {
        try {
            Cliente cliente = clienteRepository.getReferenceById(id);

            if (cartaoRepository.selectCartao(cartao.getNumeroCartao()) == null) {
                validationCartao.allValidates(cartao);
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
    public ResponseEntity<String> updateCliente(@RequestBody @Valid Cliente cliente) {
        validationCliente.allValidates(cliente);

        clienteRepository.save(cliente);

        return ResponseEntity.ok().body("Cliente atualizado com sucesso!");
    }

    //delete JPA
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCliente(@PathVariable Long id){
        try {
            clienteRepository.deleteById(id);

            return ResponseEntity.ok().body("Cliente deletado com sucesso!");
        }
        catch (EmptyResultDataAccessException e) {
            return ResponseEntity.badRequest().body("Cliente não encontrado pelo Cliente Id: " + id);
        }
    }

    // handler @validation exception
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationsExceptions(MethodArgumentNotValidException exception) {
        return HandlerValidationsExceptions.handler(exception);
    }

    // handler custom validation exception
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CustomValidationException.class)
    public String handleCustomValidationsExceptions (CustomValidationException exception){
        return HandlerCustomValidationsExceptions.handler(exception);
    }

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
