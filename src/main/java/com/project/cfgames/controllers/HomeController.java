package com.project.cfgames.controllers;

import com.project.cfgames.dtos.requests.IdListRequest;
import com.project.cfgames.dtos.requests.TituloRequest;
import com.project.cfgames.entities.Categoria;
import com.project.cfgames.entities.Produto;
import com.project.cfgames.repositories.CategoriaRepository;
import com.project.cfgames.repositories.ProdutoRepository;
import com.project.cfgames.validations.ValidationCategoria;
import com.project.cfgames.validations.ValidationProduto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
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
