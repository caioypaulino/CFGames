package com.project.cfgames.controllers;

import com.project.cfgames.entities.Cartao;
import com.project.cfgames.validations.exceptions.CustomValidationException;
import com.project.cfgames.repositories.CartaoRepository;
import com.project.cfgames.services.CartaoService;
import com.project.cfgames.validations.ValidationCartao;
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

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    @Autowired
    CartaoRepository cartaoRepository;

    @Autowired
    CartaoService cartaoService;

    @Autowired
    ValidationCartao validationCartao;

    // create JPA
    @PostMapping("/save")
    public ResponseEntity<String> saveCartao(@RequestBody @Valid Cartao cartao){
        validationCartao.allValidates(cartao);

        cartao.setBandeira(cartaoService.bandeiraCartao(cartao));
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
    public ResponseEntity<String> updateCartao(@RequestBody @Valid Cartao cartao) {
        validationCartao.allValidates(cartao);

        cartaoRepository.save(cartao);

        return ResponseEntity.ok().body("Cartão de Crédito atualizado com sucesso!");
    }

    //delete JPA
    @DeleteMapping("/delete/{numeroCartao}")
    public ResponseEntity<String> deleteCartao(@PathVariable String numeroCartao){
        try {
            if (cartaoRepository.selectClientesCartao(numeroCartao) == null || cartaoRepository.selectPedidosCartao(numeroCartao) == null) {
                cartaoRepository.deleteById(numeroCartao);
                return ResponseEntity.ok().body("Cartão de Crédito deletado com sucesso!");
            }
            else {
                return ResponseEntity.badRequest().body("Cartão de Crédito associado a um Cliente ou Pedido.");
            }
        }
        catch (EmptyResultDataAccessException e){
            return ResponseEntity.badRequest().body("Cartão de Crédito não encontrado pelo numeroCartao: " + numeroCartao);
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

    @GetMapping("/delete_cartao/{numeroCartao}")
    public ModelAndView deleteCartao(@PathVariable String numeroCartao, RedirectAttributes redirect) {
        Optional<Cartao> cartao = cartaoRepository.findById(numeroCartao);
        if (cartao.isPresent()) {
            cartaoRepository.delete(cartao.get());
            redirect.addFlashAttribute("mensagem", "Cartao deletado!");
            return new ModelAndView("redirect:/admin/painel");
        }
        else {
            throw new RuntimeException("Cartao não encontrado pelo número: " + numeroCartao);
        }
    }
}
