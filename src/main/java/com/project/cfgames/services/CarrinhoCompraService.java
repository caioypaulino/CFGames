package com.project.cfgames.services;

import com.project.cfgames.entities.ItemCarrinho;
import com.project.cfgames.entities.Produto;
import com.project.cfgames.repositories.ItemCarrinhoRepository;
import com.project.cfgames.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            Produto produto = produtoRepository.getReferenceById(item.getProduto().getId());

            valorItem = ((produto.getPreco()) * item.getQuantidade());
            item.setValorItem(valorItem);
            valorTotal += valorItem;
        }

        return valorTotal;
    }

    // calcula peso total da compra
    public Integer pesoItens(Set<ItemCarrinho> itensCarrinho){
        int pesoTotal = 0;
        int pesoItem = 0;

        for (ItemCarrinho item: itensCarrinho) {
            Produto produto = produtoRepository.getReferenceById(item.getProduto().getId());

            pesoItem = ((produto.getPeso()) * item.getQuantidade());
            pesoTotal += pesoItem;
        }

        return pesoTotal;
    }
}
