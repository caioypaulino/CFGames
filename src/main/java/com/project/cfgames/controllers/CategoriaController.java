package com.project.cfgames.controllers;

import com.project.cfgames.entities.Categoria;
import com.project.cfgames.facades.Facade;
import com.project.cfgames.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    CategoriaRepository categoriaRepository;

    Facade facade = new Facade();

    // create JPA
    @PostMapping("/save")
    public Categoria saveCategoria(@RequestBody Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    // readAll JPA
    @GetMapping("/read")
    public List<Categoria> readAllCategoria() {
        return categoriaRepository.findAll();
    }

    // readById JPA
    @GetMapping("/read/{id}")
    public Categoria readByIdCategoria(@PathVariable Long id) {
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        if (categoria.isPresent()) {
            return categoria.get();
        } else {
            throw new RuntimeException("Categoria não encontrado pelo id: " + id);
        }
    }

    // update JPA
    @PutMapping("/update")
    public Categoria updateCategoria(@RequestBody Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    // delete JPA
    @GetMapping("/delete_categoria/{id}")
    public ModelAndView deleteCategoria(@PathVariable Long id, RedirectAttributes redirect) {
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        if (categoria.isPresent()) {
            categoriaRepository.delete(categoria.get());
            redirect.addFlashAttribute("mensagem", "Categoria deletado!");
            return new ModelAndView("redirect:/admin/painel");
        } else {
            throw new RuntimeException("Categoria não encontrado pelo id: " + id);
        }
    }

    @GetMapping("/form/add")
    public ModelAndView getFormadd() {
        return new ModelAndView("cadastroCategoria");
    }

    @GetMapping("/form/update/{id}")
    public ModelAndView getFormUpdate(@PathVariable("id") Long id){
        Optional<Categoria> categoria = this.categoriaRepository.findById(id);
        ModelAndView mv = new ModelAndView("updateCategoria");
        mv.addObject("categoria", categoria);
        return mv;
    }
}
