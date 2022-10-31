package com.project.cfgames.controllers;

import com.project.cfgames.entities.Cartao;
import com.project.cfgames.repositories.CartaoRepository;
import com.project.cfgames.services.CartaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    @Autowired
    CartaoRepository cartaoRepository;

    @Autowired
    CartaoService cartaoService;

    // create JPA
    @PostMapping("/save")
    public Cartao saveCartao(@RequestBody Cartao cartao) {
        cartao.setBandeira(cartaoService.retornaBandeira(cartao.getNumeroCartao()));
        return cartaoRepository.save(cartao);
    }

    // readAll JPA
    @GetMapping("/read")
    public List<Cartao> readAllCartao() {
        return cartaoRepository.findAll();
    }

    // readById JPA
    @GetMapping("/read/{numeroCartao}")
    public Cartao readByIdCartao(@PathVariable String numeroCartao) {
        Optional<Cartao> cartao = cartaoRepository.findById(numeroCartao);
        if (cartao.isPresent()) {
            return cartao.get();
        } else {
            throw new RuntimeException("Cartao não encontrado pelo número: " + numeroCartao);
        }
    }

    // update JPA
    @PutMapping("/update")
    public Cartao updateCartao(@RequestBody Cartao cartao) {
        return cartaoRepository.save(cartao);
    }

    //delete JPA
    @DeleteMapping("/delete/{numeroCartao}")
    public void deleteCartao(@PathVariable String numeroCartao){
        Optional<Cartao> cartao = cartaoRepository.findById(numeroCartao);
        if (cartao.isPresent()) {
            cartaoRepository.delete(cartao.get());
        } else {
            throw new RuntimeException("Cartao não encontrado pelo número: " + numeroCartao);
        }
    }


    // delete JPA
    @GetMapping("/delete_cartao/{numeroCartao}")
    public ModelAndView deleteCartao(@PathVariable String numeroCartao, RedirectAttributes redirect) {
        Optional<Cartao> cartao = cartaoRepository.findById(numeroCartao);
        if (cartao.isPresent()) {
            cartaoRepository.delete(cartao.get());
            redirect.addFlashAttribute("mensagem", "Cartao deletado!");
            return new ModelAndView("redirect:/admin/painel");
        } else {
            throw new RuntimeException("Cartao não encontrado pelo número: " + numeroCartao);
        }
    }

    @GetMapping("/form/add")
    public ModelAndView getFormadd() {
        return new ModelAndView("cadastroCartao");
    }

    @GetMapping("/form/update/{numeroCartao}")
    public ModelAndView getFormUpdate(@PathVariable("numeroCartao") String numeroCartao){
        Optional<Cartao> cartao = this.cartaoRepository.findById(numeroCartao);
        ModelAndView mv = new ModelAndView("updateCartao");
        mv.addObject("cartao", cartao);
        return mv;
    }
}
