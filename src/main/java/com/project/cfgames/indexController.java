package com.project.cfgames;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class indexController {

    @GetMapping("/hello")
    public ModelAndView getHello(){
        ModelAndView mv = new ModelAndView("index");
        return mv;
    }
}
