package com.project.cfgames.validations;

import com.project.cfgames.dtos.requests.ProdutoRequest;
import com.project.cfgames.entities.Categoria;
import com.project.cfgames.entities.Produto;
import com.project.cfgames.exceptions.CustomValidationException;
import com.project.cfgames.repositories.CategoriaRepository;
import com.project.cfgames.repositories.ProdutoRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ValidationProduto {
    @Autowired
    ProdutoRepository produtoRepository;
    @Autowired
    CategoriaRepository categoriaRepository;


    // valida categoria
    @SneakyThrows
    public void categoriasValidate (Produto produto) {
        Set<Categoria> categorias = produto.getCategorias();

        for (Categoria categoria : categorias) {
            if (categoriaRepository.findById(categoria.getId()).isEmpty()) {
                throw new CustomValidationException("Categoria não encontrada pelo id: " + categoria.getId());
            }
        }
    }

    // valida codigo de barras
    @SneakyThrows
    public void codigoBarrasValidate (Produto produto) {
        if (produtoRepository.selectByCodigoBarras(produto.getCodigoBarras()) != null){
            throw new CustomValidationException("Código de barras já cadastrado.");
        }
    }

    // valida codigo de barras UPDATE
    @SneakyThrows
    public void updateCodigoBarrasValidate(ProdutoRequest request, Produto produto) {
        if (produtoRepository.selectByCodigoBarrasUpdate(produto.getId(), request.getCodigoBarras()) != null){
            throw new CustomValidationException("Código de barras já cadastrado.");
        }
    }

    public void allValidates(Produto produto) {
        categoriasValidate(produto);
        codigoBarrasValidate(produto);
    }

    public void updateAllValidates(ProdutoRequest request, Produto produto) {
        if (request.getCodigoBarras() != null) {
            updateCodigoBarrasValidate(request, produto);
        }
        if (request.getCategorias() != null) {
            categoriasValidate(produto);
        }
    }
}
