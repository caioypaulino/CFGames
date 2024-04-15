package com.project.cfgames.services;

import com.project.cfgames.dtos.requests.ItemReposicaoRequest;
import com.project.cfgames.entities.ItemCarrinho;
import com.project.cfgames.entities.ItemTroca;
import com.project.cfgames.entities.Produto;
import com.project.cfgames.repositories.ItemTrocaRepository;
import com.project.cfgames.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class EstoqueService {
    @Autowired
    ProdutoRepository produtoRepository;
    @Autowired
    ItemTrocaRepository itemTrocaRepository;

    // baixa estoque de cada produto em pedido
    public void baixarEstoque(Set<ItemCarrinho> itens) {
        for (ItemCarrinho item : itens) {
            Produto produto = item.getProduto();

            produto.baixarEstoque(item.getQuantidade());

            produtoRepository.save(produto);
        }
    }

    // repoe ao estoque cada item pedido cancelado
    public void reporEstoqueCancelamento(Set<ItemCarrinho> itens) {
        for (ItemCarrinho item : itens) {
            Produto produto = item.getProduto();

            produto.reporEstoque(item.getQuantidade());

            produtoRepository.save(produto);
        }
    }

    // repoe ao estoque cada item troca selecionado
    public void reporEstoqueTroca(Set<ItemReposicaoRequest> itens) {
        for (ItemReposicaoRequest item : itens) {
            ItemTroca itemTroca = itemTrocaRepository.getReferenceById(item.getItemTroca().getId());
            Produto produto = itemTroca.getItemCarrinho().getProduto();

            produto.reporEstoque(item.getQuantidadeReposicao());

            produtoRepository.save(produto);
        }
    }
}
