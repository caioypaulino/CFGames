package com.project.cfgames.controllers.admin;

import com.project.cfgames.dtos.mappers.CustomMapper;
import com.project.cfgames.entities.Categoria;
import com.project.cfgames.repositories.CategoriaRepository;
import com.project.cfgames.validations.ValidationCategoria;
import org.modelmapper.MappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminCategoriaController {
    @Autowired
    CategoriaRepository categoriaRepository;
    @Autowired
    ValidationCategoria validationCategoria;

    // categorias - create
    @PostMapping("/categorias/add") @RolesAllowed("ROLE_ADMIN")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<String> saveCategoria(@RequestBody @Valid Categoria categoria) {
        validationCategoria.allValidates(categoria);

        categoriaRepository.save(categoria);

        return ResponseEntity.ok().body("Categoria adicionada com sucesso!");
    }

    // categorias - readAll
    @GetMapping("/categorias") @RolesAllowed("ROLE_ADMIN")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public List<Categoria> readAllCategoria() {
        return categoriaRepository.findAll();
    }

    // categorias - readById
    @GetMapping("/categorias/buscar/{id}") @RolesAllowed("ROLE_ADMIN")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<?> readByIdCategoria(@PathVariable Long id) {
        Optional<Categoria> categoria = categoriaRepository.findById(id);

        if (categoria.isPresent()) {
            return ResponseEntity.ok().body(categoria.get());
        }
        else {
            return ResponseEntity.badRequest().body("Categoria não encontrada pelo id: " + id);
        }
    }

    // categorias - update
    @PutMapping("/categorias/update/{id}") @RolesAllowed("ROLE_ADMIN")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
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

    // categorias - delete
    @DeleteMapping("/categorias/delete/{id}") @RolesAllowed("ROLE_ADMIN")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<String> deleteCategoria(@PathVariable Long id) {
        try {
            categoriaRepository.removeCategoriasById(id);
            categoriaRepository.deleteById(id);

            return ResponseEntity.ok().body("Categoria deletada com sucesso!");
        }
        catch (EmptyResultDataAccessException ex) {
            return ResponseEntity.badRequest().body("Categoria não encontrada pela Categoria Id: " + id);
        }
    }
}
