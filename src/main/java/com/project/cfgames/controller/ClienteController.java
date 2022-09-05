package com.project.cfgames.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.cfgames.entities.Cliente;
import com.project.cfgames.repository.ClienteRepository;
import com.project.cfgames.service.ClienteService;


@Controller
public class ClienteController {
    
    @Autowired
    private ClienteService clienteService;

    @GetMapping("/cliente/form/add")
    public ModelAndView getFormadd() { 
        ModelAndView mv = new ModelAndView("cadastro");
        return mv;
        
    }

    @PostMapping("/cliente/form/save")
    public String saveCliente(@Valid Cliente cliente, BindingResult result, RedirectAttributes redirect){
        
        if (result.hasErrors()) {
            redirect.addFlashAttribute("Mensagem", "Erro");
            return "redirect:/cliente/form/add";
        }

        this.clienteService.save(cliente);

        return "redirect:/cliente/form/add";

    }

}
