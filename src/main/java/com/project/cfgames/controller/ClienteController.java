package com.project.cfgames.controller;

import java.util.List;

import javax.naming.Binding;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.cfgames.dao.ClienteDao;
import com.project.cfgames.entities.Cliente;


@Controller
public class ClienteController {
    
    @Autowired
    private ClienteDao clienteDao;

    @GetMapping("/cliente/form/add")
    public ModelAndView getFormadd() { 
        ModelAndView mv = new ModelAndView("cadastro");
        return mv;
        
    }

    @PostMapping("/cliente/form/save")
    public String saveCliente(Cliente cliente, BindingResult result, RedirectAttributes redirect){
        
        this.clienteDao.create(cliente);
        return null;

    }

}
