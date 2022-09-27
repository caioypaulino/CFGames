package com.project.cfgames.controllers;

import com.project.cfgames.entities.Cartao;
import com.project.cfgames.facade.Facade;
import com.project.cfgames.repository.CartaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@RestController
public class CartaoController {

    @Autowired
    CartaoRepository cartaoRepository;

    Facade facade = new Facade();

    // create JPA
    @PostMapping("/cartao/save")
    public Cartao saveCartao(@RequestBody Cartao cartao) {
        return cartaoRepository.save(cartao);
    }

    // readAll JPA
    @GetMapping("/cartao")
    public List<Cartao> readAllCartao() {
        return cartaoRepository.findAll();
    }

    // readById JPA
    @GetMapping("/cartao/{id}")
    public Cartao readByIdCartao(@PathVariable Long id) {
        Optional<Cartao> cartao = cartaoRepository.findById(id);
        if (cartao.isPresent()) {
            return cartao.get();
        } else {
            throw new RuntimeException("Cartao não encontrado pelo id: " + id);
        }
    }

    // update JPA
    @PutMapping("/cartao")
    public Cartao updateCartao(@RequestBody Cartao cartao) {
        return cartaoRepository.save(cartao);
    }

    // delete JPA
    @GetMapping("/delete_cartao/{id}")
    public ModelAndView deleteCartao(@PathVariable Long id, RedirectAttributes redirect) {
        Optional<Cartao> cartao = cartaoRepository.findById(id);
        if (cartao.isPresent()) {
            cartaoRepository.delete(cartao.get());
            redirect.addFlashAttribute("mensagem", "Cartao deletado!");
            return new ModelAndView("redirect:/admin/painel");
        } else {
            throw new RuntimeException("Cartao não encontrado pelo id: " + id);
        }
    }

    @GetMapping("/cartao/form/add")
    public ModelAndView getFormadd() {
        return new ModelAndView("cadastroCartao");
    }

    @GetMapping("/cartao/form/update/{id}")
    public ModelAndView getFormUpdate(@PathVariable("id") Long id){
        Optional<Cartao> cartao = this.cartaoRepository.findById(id);
        ModelAndView mv = new ModelAndView("updateCartao");
        mv.addObject("cartao", cartao);
        return mv;
    }
}
