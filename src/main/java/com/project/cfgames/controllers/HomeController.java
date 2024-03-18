package com.project.cfgames.controllers;

import com.project.cfgames.dtos.requests.IdListRequest;
import com.project.cfgames.entities.Produto;
import com.project.cfgames.repositories.CategoriaRepository;
import com.project.cfgames.repositories.ProdutoRepository;
import com.project.cfgames.validations.ValidationCategoria;
import com.project.cfgames.validations.ValidationProduto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/home")
public class HomeController {
    @Autowired
    ProdutoRepository produtoRepository;

    // readAll JPA
    @GetMapping()
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public List<Produto> selectAllProdutos() {
        return produtoRepository.findAll();
    }

    // readByTitulo JPA
    @GetMapping("/buscar/titulo")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public List<Produto> selectByTituloProdutos(@RequestBody String titulo) {
        return produtoRepository.findByTitulo(titulo);
    }

    // readByCategorias JPA
    @GetMapping("/buscar/categorias")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public List<Produto> selectByCategoriasProdutos(@RequestBody IdListRequest idListRequest) {
        return produtoRepository.findByCategorias(idListRequest.getIdList());
    }

    // readByPlataformas JPA
    @GetMapping("/buscar/plataformas")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public List<Produto> selectByPlataformasProdutos(@RequestBody IdListRequest idListRequest) {
        return produtoRepository.findByPlataformas(idListRequest.getIdList());
    }

    // readByPrecoAsc JPA
    @GetMapping("/preco/asc")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public List<Produto> selectByPrecoProdutosAsc() {
        return produtoRepository.findByPrecoAsc();
    }

    // readByPrecoDesc JPA
    @GetMapping("/preco/desc")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public List<Produto> selectByPrecoProdutosDesc() {
        return produtoRepository.findByPrecoDesc();
    }

    // readByDataLancamentoDesc JPA
    @GetMapping("/data/desc")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public List<Produto> selectByDataLancamentoDesc() {
        return produtoRepository.findByDataLancamentoDesc();
    }
}
