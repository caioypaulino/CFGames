package com.project.cfgames.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import com.project.cfgames.Service.ClienteService;
import com.project.cfgames.entity.Cliente;

@Controller
public class ClienteController {
    
    @Autowired
    private ClienteService clienteService;

    @GetMapping("/cliente/form/add")
    public ModelAndView getFormadd() {
        
        ModelAndView mv = new ModelAndView("cadastro");
        List<Cliente> clienteList = this.clienteService.getClienteList();
        mv.addObject("clienteList", clienteList);

        return mv;
        
    }
}
