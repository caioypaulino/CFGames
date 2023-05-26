package com.project.cfgames.services;

import org.springframework.stereotype.Service;

@Service
public class ProdutoService {
    // retorna quantidade estoque atualizado
    public Integer adicionarEstoque(Integer quantidadeEstoque, Integer quantidadeRequest) {
        return quantidadeEstoque + quantidadeRequest;
    }

}
