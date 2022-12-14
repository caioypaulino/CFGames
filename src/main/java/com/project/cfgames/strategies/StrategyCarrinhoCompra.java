package com.project.cfgames.strategies;

import com.project.cfgames.entities.CarrinhoCompra;
import com.project.cfgames.entities.Cliente;
import com.project.cfgames.entities.ItemCarrinho;
import com.project.cfgames.entities.Produto;
import com.project.cfgames.repositories.ClienteRepository;
import com.project.cfgames.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class StrategyCarrinhoCompra {

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    ClienteRepository clienteRepository;

    // valida cliente
    public boolean clienteValidate(CarrinhoCompra carrinhoCompra){
        Optional<Cliente> cliente = clienteRepository.findById(carrinhoCompra.getCliente().getId());

        if (cliente.isPresent()){
            return true;
        }
        else {
            throw new RuntimeException("Cliente não encontrado");
        }
    }

    //valida produto
    public boolean produtoValidate(CarrinhoCompra carrinhoCompra){
        Set<ItemCarrinho> itensCarrinho = carrinhoCompra.getItens();

        for (ItemCarrinho item: itensCarrinho) {
            Optional<Produto> produto = produtoRepository.findById(item.getProduto().getId());

            if (produto.isPresent()) {
                return true;
            }
            else {
                throw new RuntimeException("ID Produto inválido");
            }
        }
        throw new RuntimeException("Produto inválido");
    }

    // valida quantidade em estoque
    public boolean quantidadeValidate(CarrinhoCompra carrinhoCompra){
        Set<ItemCarrinho> itensCarrinho = carrinhoCompra.getItens();

        for (ItemCarrinho item: itensCarrinho) {
            Optional<Produto> produto = produtoRepository.findById(item.getProduto().getId());

            if (produto.isPresent()){
                int quantidadeEstoque = produto.get().getQuantidade();
                int quantidadeItem = item.getQuantidade();

                if (quantidadeItem > quantidadeEstoque){
                    throw new RuntimeException("Quantidade do produto_id: " + produto.get().getId() + " indisponível em estoque");
                }
                else {
                    return true;
                }
            }
        }
        throw new RuntimeException("Quantidade em estoque indisponível");
    }

    // todas as validações anteriores
    public boolean allValidates(CarrinhoCompra carrinhoCompra) {
        if (clienteValidate(carrinhoCompra) && produtoValidate(carrinhoCompra) && quantidadeValidate(carrinhoCompra)){
            return true;
        }
        else {
            throw new RuntimeException("Erro validação");
        }
    }
}
