package com.project.cfgames.controllers;

import com.project.cfgames.dtos.mappers.CustomMapper;
import com.project.cfgames.dtos.requests.ProdutoRequest;
import com.project.cfgames.entities.Categoria;
import com.project.cfgames.entities.Produto;
import com.project.cfgames.repositories.CategoriaRepository;
import com.project.cfgames.repositories.ProdutoRepository;
import com.project.cfgames.validations.ValidationCategoria;
import com.project.cfgames.validations.ValidationProduto;
import com.project.cfgames.validations.exceptions.CustomValidationException;
import com.project.cfgames.validations.handlers.HandlerCustomValidationsExceptions;
import com.project.cfgames.validations.handlers.HandlerValidationsExceptions;
import org.modelmapper.MappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    ValidationProduto validationProduto;

    @Autowired
    ValidationCategoria validationCategoria;

    // create JPA
    @PostMapping("/save")
    public ResponseEntity<String> saveProduto(@RequestBody @Valid Produto produto) {
        validationProduto.allValidates(produto);

        produtoRepository.save(produto);

        return ResponseEntity.ok().body("Produto adicionado com sucesso!");
    }

    // readAll JPA
    @GetMapping("/read")
    public List<Produto> readAllProduto() {
        return produtoRepository.findAll();
    }

    // readById JPA
    @GetMapping("/read/{id}")
    public ResponseEntity<?> readByIdProduto(@PathVariable Long id) {
        Optional<Produto> produto = produtoRepository.findById(id);

        if (produto.isPresent()) {
            return ResponseEntity.ok().body(produto.get());
        }
        else {
            return ResponseEntity.badRequest().body("Produto não encontrado pelo id: " + id);
        }
    }

    // update JPA
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateProduto(@PathVariable Long id, @RequestBody @Valid ProdutoRequest request) {
        try {
            Produto produto = produtoRepository.getReferenceById(id);

            CustomMapper.update(request, produto);

            if (request.getCodigoBarras() != null || request.getCategorias() != null ) {
                validationProduto.updateCodigoBarrasValidate(produto);
                validationProduto.categoriasValidate(produto);
            }

            produtoRepository.save(produto);

            return ResponseEntity.ok().body("Produto atualizado com sucesso!");
        }
        catch (EntityNotFoundException | MappingException ex) {
            return ResponseEntity.badRequest().body("Produto não encontrado pelo id: " + id);
        }
    }

    // add categoria
    @PutMapping("/{id}/categoria")
    public ResponseEntity<String> addCategoria(@PathVariable Long id, @RequestBody Categoria categoria) {
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

    // remove categoria
    @DeleteMapping("/{id}/categoria")
    public ResponseEntity<String> removeCategoria(@PathVariable Long id, @RequestBody Categoria categoria) {
        if (categoriaRepository.selectCategoriaProduto(id, categoria.getId()) != null) {
            produtoRepository.removeCategoria(id, categoria.getId());
            return ResponseEntity.ok("Categoria removida com sucesso!");
        }
        else {
            return ResponseEntity.badRequest().body("Produto ou Categoria não encontrados pelo Produto Id: " + id + " e Categoria Id: " + categoria.getId());
        }
    }

    // delete JPA
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduto(@PathVariable Long id) {
        try {
            produtoRepository.removeAllCategorias(id);
            produtoRepository.deleteById(id);

            return ResponseEntity.ok().body("Produto deletado com sucesso!");
        }
        catch (EmptyResultDataAccessException e) {
            return ResponseEntity.badRequest().body("Produto não encontrado pelo Produto Id: " + id);
        }
    }

    // handler @validation exception
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationsExceptions(MethodArgumentNotValidException exception) {
        return HandlerValidationsExceptions.handler(exception);
    }

    // handler custom validation exception
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CustomValidationException.class)
    public String handleCustomValidationsExceptions (CustomValidationException exception){
        return HandlerCustomValidationsExceptions.handler(exception);
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
