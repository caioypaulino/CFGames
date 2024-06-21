package com.project.cfgames.controllers;

import com.project.cfgames.dtos.requests.GeminiRequest;
import com.project.cfgames.entities.Categoria;
import com.project.cfgames.entities.Produto;
import com.project.cfgames.repositories.CategoriaRepository;
import com.project.cfgames.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/home")
public class HomeController {
    @Autowired
    ProdutoRepository produtoRepository;
    @Autowired
    CategoriaRepository categoriaRepository;

    // readAll JPA
    @GetMapping()
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public List<Produto> selectAllProdutos() {
        return produtoRepository.findAll();
    }

    // categorias - readAll
    @GetMapping("/buscar/categorias")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public List<Categoria> selectAllCategorias() {
        return categoriaRepository.findAll();
    }

    // readByTitulo JPA
    @GetMapping("/buscar/titulo={titulo}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public List<Produto> selectByTituloProdutos(@PathVariable String titulo) {
        return produtoRepository.findByTitulo(titulo);
    }
}
