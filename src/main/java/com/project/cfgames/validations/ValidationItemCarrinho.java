package com.project.cfgames.validations;

import com.project.cfgames.entities.ItemCarrinho;
import com.project.cfgames.entities.Produto;
import com.project.cfgames.repositories.ProdutoRepository;
import com.project.cfgames.validations.exceptions.CustomValidationException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidationItemCarrinho {
    @Autowired
    ProdutoRepository produtoRepository;

    //valida produto
    @SneakyThrows
    public void produtoValidate(ItemCarrinho itemCarrinho){
        if (itemCarrinho.getProduto().getId() == null) {
            throw new CustomValidationException("Produto id null.");
        }
        else if (produtoRepository.findById(itemCarrinho.getProduto().getId()).isEmpty()) {
            throw new CustomValidationException("Produto não encontrado pelo id: " + itemCarrinho.getProduto().getId());
        }
    }

    // valida quantidade em estoque
    @SneakyThrows
    public void quantidadeValidate(ItemCarrinho itemCarrinho){
        Produto produto = produtoRepository.getReferenceById(itemCarrinho.getProduto().getId());

        // quantidade estoque < quantidade item carrinho
        if (produto.getQuantidade() < itemCarrinho.getQuantidade()){
            throw new CustomValidationException("Quantidade do produto id: " + itemCarrinho.getProduto().getId() + " indisponível em estoque");
        }
    }

    // todas as validações anteriores
    public void allValidates(ItemCarrinho itemCarrinho) {
        produtoValidate(itemCarrinho);
        quantidadeValidate(itemCarrinho);
    }
}
