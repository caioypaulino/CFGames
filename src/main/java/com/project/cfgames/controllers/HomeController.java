package com.project.cfgames.controllers;

import com.project.cfgames.clients.responses.GeminiResponse;
import com.project.cfgames.dtos.requests.IdListRequest;
import com.project.cfgames.dtos.requests.TituloRequest;
import com.project.cfgames.entities.Categoria;
import com.project.cfgames.entities.Pedido;
import com.project.cfgames.entities.Produto;
import com.project.cfgames.repositories.CategoriaRepository;
import com.project.cfgames.repositories.ProdutoRepository;
import com.project.cfgames.services.GeminiService;
import com.project.cfgames.validations.ValidationCategoria;
import com.project.cfgames.validations.ValidationProduto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/home")
public class HomeController {
    @Autowired
    ProdutoRepository produtoRepository;
    @Autowired
    CategoriaRepository categoriaRepository;
    @Autowired
    GeminiService geminiService;

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

    // Gemini IA
    @PostMapping("/gemini")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public GeminiResponse perguntarGemini(@RequestBody String nomeJogo) {
        return geminiService.gerarResposta(nomeJogo);
    }
}
