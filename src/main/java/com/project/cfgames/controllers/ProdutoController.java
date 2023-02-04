package com.project.cfgames.controllers;

import com.project.cfgames.entities.Categoria;
import com.project.cfgames.entities.Produto;
import com.project.cfgames.repositories.CategoriaRepository;
import com.project.cfgames.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    CategoriaRepository categoriaRepository;

    // create JPA
    @PostMapping("/save")
    public Produto saveProduto(@RequestBody Produto produto) {
        return produtoRepository.save(produto);
    }

    // readAll JPA
    @GetMapping("/read")
    public List<Produto> readAllProduto() {
        return produtoRepository.findAll();
    }

    // readById JPA
    @GetMapping("/read/{id}")
    public Produto readByIdProduto(@PathVariable Long id) {
        Optional<Produto> produto = produtoRepository.findById(id);
        if (produto.isPresent()) {
            return produto.get();
        } else {
            throw new RuntimeException("Produto não encontrado pelo id: " + id);
        }
    }

    // update JPA
    @PutMapping("/update")
    public Produto updateProduto(@RequestBody Produto produto) {
        return produtoRepository.save(produto);
    }

    // add categoria
    @PutMapping("/{id}/categoria/{categoriaId}")
    public Produto addCategoria(@PathVariable Long id, @PathVariable Long categoriaId) {
        Produto produto = produtoRepository.findById(id).get();
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

    @GetMapping("/form/add")
    public ModelAndView getFormadd() {
        return new ModelAndView("cadastroProduto");
    }

    @GetMapping("/form/update/{id}")
    public ModelAndView getFormUpdate(@PathVariable("id") Long id){
        Optional<Produto> produto = this.produtoRepository.findById(id);
        ModelAndView mv = new ModelAndView("updateProduto");
        mv.addObject("produto", produto);
        return mv;
    }
}
