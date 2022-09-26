package com.project.cfgames.controllers;

import com.project.cfgames.entities.Categoria;
import com.project.cfgames.entities.Produto;
import com.project.cfgames.facade.Facade;
import com.project.cfgames.repository.CategoriaRepository;
import com.project.cfgames.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@RestController
public class ProdutoController {

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    CategoriaRepository categoriaRepository;

    Facade facade = new Facade();

    // create JPA
    @PostMapping("/produto/save")
    public Produto saveProduto(@RequestBody Produto produto) {
        return produtoRepository.save(produto);
    }

    // readAll JPA
    @GetMapping("/produto")
    public List<Produto> readAllProduto() {
        return produtoRepository.findAll();
    }

    // readById JPA
    @GetMapping("/produto/{id}")
    public Produto readByIdProduto(@PathVariable Long id) {
        Optional<Produto> produto = produtoRepository.findById(id);
        if (produto.isPresent()) {
            return produto.get();
        } else {
            throw new RuntimeException("Produto não encontrado pelo id: " + id);
        }
    }

    // update JPA
    @PutMapping("/produto")
    public Produto updateProduto(@RequestBody Produto produto) {
        return produtoRepository.save(produto);
    }

    // add categoria
    @PutMapping("/produto/{produtoId}/categoria/{categoriaId}")
    public Produto addCategoria(@PathVariable Long produtoId, @PathVariable Long categoriaId) {
        Produto produto = produtoRepository.findById(produtoId).get();
        Categoria categoria = categoriaRepository.findById(categoriaId).get();
        produto.categoriasProduto(categoria);
        return produtoRepository.save(produto);
    }

    // delete JPA
    @GetMapping("/delete_produto/{id}")
    public ModelAndView deleteProduto(@PathVariable Long id, RedirectAttributes redirect) {
        Optional<Produto> produto = produtoRepository.findById(id);
        if (produto.isPresent()) {
            produtoRepository.delete(produto.get());
            redirect.addFlashAttribute("mensagem", "Produto deletado!");
            return new ModelAndView("redirect:/admin/painel");
        } else {
            throw new RuntimeException("Produto não encontrado pelo id: " + id);
        }
    }

    @GetMapping("/produto/form/add")
    public ModelAndView getFormadd() {
        return new ModelAndView("cadastroProduto");
    }

    @GetMapping("/produto/form/update/{id}")
    public ModelAndView getFormUpdate(@PathVariable("id") Long id){
        Optional<Produto> produto = this.produtoRepository.findById(id);
        ModelAndView mv = new ModelAndView("updateProduto");
        mv.addObject("produto", produto);
        return mv;
    }
}
