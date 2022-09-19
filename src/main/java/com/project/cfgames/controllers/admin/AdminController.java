package com.project.cfgames.controllers.admin;

import com.project.cfgames.dao.ClienteDao;
import com.project.cfgames.entities.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class AdminController {

    @Autowired
    private ClienteDao clienteDao;

    @GetMapping("/admin/painel")
    public ModelAndView paineladmin() {
        List<Cliente> clienteList = this.clienteDao.findAll();

        ModelAndView mv = new ModelAndView("painelAdmin");
        mv.addObject("clienteList", clienteList);
        return mv;
    }


    @PostMapping("/update/{id}")
    public ModelAndView updateUser(@PathVariable("id") long id, @Valid Cliente cliente,
                                   BindingResult result, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            cliente.setId(id);
            redirect.addFlashAttribute("mensagem", "Cliente não encontrado para atualizar");
            return new ModelAndView("redirect:/admin/painel");
        }

        clienteDao.save(cliente);
        redirect.addFlashAttribute("mensagem", "Cliente atualizado");
        return new ModelAndView("redirect:/admin/painel");
    }

}
