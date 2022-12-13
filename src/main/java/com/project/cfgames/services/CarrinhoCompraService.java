package com.project.cfgames.services;

import com.project.cfgames.entities.ItemCarrinho;
import com.project.cfgames.entities.Produto;
import com.project.cfgames.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class CarrinhoCompraService {

    @Autowired
    ProdutoRepository produtoRepository;

    // calcula valor total da compra e insere valor dos itens no carrinho (preço item * quantidade selecionada)
    public Float valorItens(Set<ItemCarrinho> itensCarrinho){
        float valorTotal = 0f;
        float valorItem = 0f;

        for (ItemCarrinho item: itensCarrinho) {
            Optional<Produto> produto = produtoRepository.findById(item.getProduto().getId());
            valorItem = ((produto.get().getPreco()) * item.getQuantidade());
            item.setValorItem(valorItem);
            valorTotal += valorItem;
        }

        return valorTotal;
    }
}