package com.project.cfgames.controllers;

import com.project.cfgames.entities.Cartao;
import com.project.cfgames.repositories.CartaoRepository;
import com.project.cfgames.services.CartaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityNotFoundException;
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
    public ResponseEntity<String> saveCartao(@RequestBody Cartao cartao) {
        cartao.setBandeira(cartaoService.retornaBandeira(cartao.getNumeroCartao()));
        cartaoRepository.save(cartao);

        return ResponseEntity.ok().body("Cartão de Crédito adicionado com sucesso!");
    }

    // readAll JPA
    @GetMapping("/read")
    public List<Cartao> readAllCartao() {
        return cartaoRepository.findAll();
    }

    // readById JPA
    @GetMapping("/read/{numeroCartao}")
    public ResponseEntity<?> readByIdCartao(@PathVariable String numeroCartao) {
        Optional<Cartao> cartao = cartaoRepository.findById(numeroCartao);

        if (cartao.isPresent()) {
            return ResponseEntity.ok().body(cartao.get());
        }
        else {
            return ResponseEntity.badRequest().body("Cartao não encontrado pelo número: " + numeroCartao);
        }
    }

    // update JPA
    @PutMapping("/update")
    public ResponseEntity<String> updateCartao(@RequestBody Cartao cartao) {
        cartaoRepository.save(cartao);

        return ResponseEntity.ok().body("Cartão de Crédito atualizado com sucesso!");
    }

    //delete JPA
    @DeleteMapping("/delete/{numeroCartao}")
    public ResponseEntity<String> deleteCartao(@PathVariable String numeroCartao){
        try {
            Cartao cartao = cartaoRepository.getReferenceById(numeroCartao);

            cartaoRepository.delete(cartao);
            return ResponseEntity.ok().body("Cartão de Crédito deletado com sucesso!");
        }
        catch (EntityNotFoundException e){
            return ResponseEntity.badRequest().body("Cartão de Crédito não encontrado pelo numeroCartao: " + numeroCartao);
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
