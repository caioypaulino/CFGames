package com.project.cfgames.controllers;

import com.project.cfgames.dtos.mappers.CustomMapper;
import com.project.cfgames.entities.Categoria;
import com.project.cfgames.repositories.CategoriaRepository;
import com.project.cfgames.validations.ValidationCategoria;
import org.modelmapper.MappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {
    @Autowired
    ValidationCategoria validationCategoria;
    @Autowired
    CategoriaRepository categoriaRepository;

    // create JPA
    @PostMapping("/save")
    public ResponseEntity<String> saveCategoria(@RequestBody @Valid Categoria categoria) {
        validationCategoria.allValidates(categoria);

        categoriaRepository.save(categoria);

        return ResponseEntity.ok().body("Categoria adicionada com sucesso!");
    }

    // readAll JPA
    @GetMapping("/read")
    public List<Categoria> readAllCategoria() {
        return categoriaRepository.findAll();
    }

    // readById JPA
    @GetMapping("/read/{id}")
    public ResponseEntity<?> readByIdCategoria(@PathVariable Long id) {
        Optional<Categoria> categoria = categoriaRepository.findById(id);

        if (categoria.isPresent()) {
            return ResponseEntity.ok().body(categoria.get());
        }
        else {
            return ResponseEntity.badRequest().body("Categoria não encontrada pelo id: " + id);
        }
    }

    // update JPA
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateCategoria(@PathVariable Long id, @RequestBody @Valid Categoria request) {
        try {
            validationCategoria.nomeValidate(request.getNome(), id);

            Categoria categoria = categoriaRepository.getReferenceById(id);

            CustomMapper.update(request, categoria);

            categoriaRepository.save(categoria);

            return ResponseEntity.ok().body("Categoria atualizada com sucesso!");
        }
        catch (EntityNotFoundException | MappingException ex) {
            return ResponseEntity.badRequest().body("Categoria não encontrada pelo id: " + id);
        }
    }

    // delete JPA
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCategoria(@PathVariable Long id) {
        try {
            categoriaRepository.deleteById(id);

            return ResponseEntity.ok().body("Categoria deletada com sucesso!");
        }
        catch (EmptyResultDataAccessException ex) {
            return ResponseEntity.badRequest().body("Categoria não encontrada pela Categoria Id: " + id);
        }
    }
}
