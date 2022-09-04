package com.project.cfgames;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ClienteController {

    @GetMapping("/cliente/form/add")
    public ModelAndView getFormAdd(){
        ModelAndView mv = new ModelAndView("clienteform");
        return mv;
    }    
}
