package com.project.cfgames.controllers.admin;

import com.project.cfgames.dao.ClienteDao;
import com.project.cfgames.entities.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

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

    @GetMapping("/edit/{id}")
    public ModelAndView getEdit(@PathVariable Long id) {

        ModelAndView mv = new ModelAndView("updateCliente");
        List<Cliente> clienteList = this.clienteDao.findAll();
        mv.addObject("clienteList", clienteList);

        Optional<Cliente> cliente = clienteDao.findById(id);
        mv.addObject("cliente", cliente);

        return mv;

    }
}
