package com.project.cfgames.controllers.admin;

import com.project.cfgames.dtos.mappers.CustomMapper;
import com.project.cfgames.dtos.requests.ProdutoRequest;
import com.project.cfgames.dtos.requests.QuantidadeRequest;
import com.project.cfgames.entities.Categoria;
import com.project.cfgames.entities.Produto;
import com.project.cfgames.repositories.CategoriaRepository;
import com.project.cfgames.repositories.ProdutoRepository;
import com.project.cfgames.services.ProdutoService;
import com.project.cfgames.validations.ValidationCategoria;
import com.project.cfgames.validations.ValidationProduto;
import org.modelmapper.MappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminProdutoController {
    @Autowired
    ValidationProduto validationProduto;
    @Autowired
    ProdutoRepository produtoRepository;
    @Autowired
    ProdutoService produtoService;
    @Autowired
    CategoriaRepository categoriaRepository;
    @Autowired
    ValidationCategoria validationCategoria;

    // produtos - create
    @PostMapping("/produtos/add") @RolesAllowed("ROLE_ADMIN")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<String> addProduto(@RequestBody @Valid Produto produto) {
        validationProduto.allValidates(produto);

        produtoRepository.save(produto);

        return ResponseEntity.ok().body("Produto adicionado com sucesso!");
    }

    // produtos - readAll
    @GetMapping("/produtos") @RolesAllowed("ROLE_ADMIN")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public List<Produto> readAllProduto() {
        return produtoRepository.findAll();
    }

    // produtos - readById
    @GetMapping("/produtos/buscar/{id}") @RolesAllowed("ROLE_ADMIN")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<?> readByIdProduto(@PathVariable Long id) {
        Optional<Produto> produto = produtoRepository.findById(id);

        if (produto.isPresent()) {
            return ResponseEntity.ok().body(produto.get());
        }
        else {
            return ResponseEntity.badRequest().body("Produto não encontrado pelo id: " + id);
        }
    }

    // produtos - update
    @PutMapping("/produtos/update/{id}") @RolesAllowed("ROLE_ADMIN")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<String> updateProduto(@PathVariable Long id, @RequestBody @Valid ProdutoRequest request) {
        try {
            Produto produto = produtoRepository.getReferenceById(id);

            validationProduto.updateAllValidates(request, produto);

            CustomMapper.update(request, produto);

            produtoRepository.save(produto);

            return ResponseEntity.ok().body("Produto atualizado com sucesso!");
        }
        catch (EntityNotFoundException | MappingException ex) {
            return ResponseEntity.badRequest().body("Produto não encontrado pelo id: " + id);
        }
    }

    // produtos - alterar estoque
    @PutMapping("produtos/update/estoque/produto/{id}") @RolesAllowed("ROLE_ADMIN")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<String> updateEstoque(@PathVariable Long id, @RequestBody @Valid QuantidadeRequest request) {
        try {
            Produto produto = produtoRepository.getReferenceById(id);

            produto.setQuantidade(produtoService.adicionarEstoque(produto.getQuantidade(), request.getQuantidade()));

            produtoRepository.save(produto);

            return ResponseEntity.ok().body("Estoque atualizado com sucesso!");
        }
        catch (EntityNotFoundException | MappingException ex) {
            return ResponseEntity.badRequest().body("Produto não encontrado pelo id: " + id);
        }
    }

    // produtos - adiocionar categoria
    @PutMapping("/produtos/add/categoria/produto/{id}") @RolesAllowed("ROLE_ADMIN")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<String> addCategoriaProduto(@PathVariable Long id, @RequestBody Categoria categoria) {
        try {
            Produto produto = produtoRepository.getReferenceById(id);

            Categoria categoriaR;

            if (categoria.getId() == null && categoria.getNome() != null) {
                validationCategoria.allValidates(categoria);

                categoriaRepository.save(categoria);

                categoriaR = categoriaRepository.selectCategoriaByName(categoria.getNome());
            }
            else {
                categoriaR = categoriaRepository.getReferenceById(categoria.getId());
            }

            produto.addCategoriasProduto(categoriaR);
            produtoRepository.save(produto);

            return ResponseEntity.ok().body("Categoria " + categoriaR.getNome()+ " adicionada com sucesso ao Produto Id: " + id);
        }
        catch (EntityNotFoundException ex) {
            return ResponseEntity.badRequest().body("Produto não encontrado pelo Id: " + id);
        }
        catch (JpaObjectRetrievalFailureException ex) {
            return ResponseEntity.badRequest().body("Categoria não encontrada pelo Id: " + categoria.getId());
        }
    }

    // produtos - remover categoria
    @DeleteMapping("/produtos/remove/categoria/produto/{id}") @RolesAllowed("ROLE_ADMIN")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<String> removeCategoriaProduto(@PathVariable Long id, @RequestBody Categoria categoria) {
        if (categoriaRepository.selectCategoriaProduto(id, categoria.getId()) != null) {
            produtoRepository.removeCategoria(id, categoria.getId());

            return ResponseEntity.ok("Categoria removida com sucesso!");
        }
        else {
            return ResponseEntity.badRequest().body("Produto ou Categoria não encontrados pelo Produto Id: " + id + " e Categoria Id: " + categoria.getId());
        }
    }

    // produtos - delete
    @DeleteMapping("/produtos/delete/{id}") @RolesAllowed("ROLE_ADMIN")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<String> deleteProduto(@PathVariable Long id) {
        try {
            produtoRepository.removeAllCategorias(id);
            produtoRepository.deleteById(id);

            return ResponseEntity.ok().body("Produto deletado com sucesso!");
        }
        catch (EmptyResultDataAccessException ex) {
            return ResponseEntity.badRequest().body("Produto não encontrado pelo Produto Id: " + id);
        }
    }

    // handler Plataforma Enum type Json exception
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public String handleCustomValidationsExceptions (){
        return "Plataforma inválida(Ex:\n0- XBOX360,\n1- XBOXONE,\n2- XBOXS,\n3- PS3,\n4- PS4,\n5- PS5,\n6- PSP,\n7- NINTENDOWII,\n8- NINTENDODS,\n9- NINTENDOSWITCH).\n"
                + "<br></br>ou\n" + "<br></br>Status Produto inválido(Ex:\n0- INATIVO,\n1- ATIVO,\n2- FORA_DE_MERCADO).\n";
    }
}
