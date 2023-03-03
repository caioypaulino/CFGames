package com.project.cfgames.validations;

import com.project.cfgames.entities.Categoria;
import com.project.cfgames.repositories.CategoriaRepository;
import com.project.cfgames.validations.exceptions.CustomValidationException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidationCategoria {
    @Autowired
    CategoriaRepository categoriaRepository;

    // valida categoria já existente
    @SneakyThrows
    public void nomeValidate(Categoria categoria) {
        if (categoriaRepository.selectCategoriaByName(categoria.getNome()) != null){
            throw new CustomValidationException("Categoria nome: " + categoria.getNome() + " ,já existente.");
        }
    }

    @SneakyThrows
    public void nomeValidate(String nomeCategoria, Long id){
        if (categoriaRepository.selectCategoriaByNameAndId(nomeCategoria, id) != null) {
            throw new CustomValidationException("Categoria nome: " + nomeCategoria + " ,já existente.");
        }
    }

    public void allValidates(Categoria categoria) {
        nomeValidate(categoria);
    }
}
