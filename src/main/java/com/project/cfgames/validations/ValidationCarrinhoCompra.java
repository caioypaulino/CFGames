package com.project.cfgames.validations;

import com.project.cfgames.dtos.requests.CarrinhoCompraRequest;
import com.project.cfgames.entities.CarrinhoCompra;
import com.project.cfgames.entities.ItemCarrinho;
import com.project.cfgames.entities.Produto;
import com.project.cfgames.repositories.ClienteRepository;
import com.project.cfgames.repositories.ItemCarrinhoRepository;
import com.project.cfgames.repositories.ProdutoRepository;
import com.project.cfgames.validations.exceptions.CustomValidationException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ValidationCarrinhoCompra {

    @Autowired
    ProdutoRepository produtoRepository;
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    ValidationItemCarrinho validationItemCarrinho;
    @Autowired
    ItemCarrinhoRepository itemCarrinhoRepository;

    // valida cliente
    @SneakyThrows
    public void clienteValidate(CarrinhoCompra carrinhoCompra){
        if (carrinhoCompra.getCliente().getId() == null) {
            throw new CustomValidationException("Cliente id null.");
        }
        else if (clienteRepository.findById(carrinhoCompra.getCliente().getId()).isEmpty()){
            throw new CustomValidationException("Cliente não encontrado pelo id: " + carrinhoCompra.getCliente().getId());
        }
    }

    // valida produtos
    @SneakyThrows
    public void produtosValidate(CarrinhoCompra carrinhoCompra){
        Set<ItemCarrinho> itensCarrinho = carrinhoCompra.getItens();

        for (ItemCarrinho item: itensCarrinho) {
            validationItemCarrinho.produtoValidate(item);
        }
    }

    // valida quantidade em estoque
    @SneakyThrows
    public void quantidadeValidate(CarrinhoCompra carrinhoCompra){
        Set<ItemCarrinho> itensCarrinho = carrinhoCompra.getItens();

        for (ItemCarrinho item: itensCarrinho) {
            validationItemCarrinho.quantidadeValidate(item);
        }
    }

    // valida produtos UPDATE
    @SneakyThrows
    public void updateProdutosValidate(CarrinhoCompraRequest request){
        Set<ItemCarrinho> itensCarrinho = request.getItensCarrinho();

        for (ItemCarrinho item: itensCarrinho) {
            if (item.getId() == null) {
                throw new CustomValidationException("Item id null.");
            }
            else if (itemCarrinhoRepository.findById(item.getId()).isEmpty()) {
                throw new CustomValidationException("Item Carrinho não encontrado pelo id: " + item.getId());
            }
            else {
                validationItemCarrinho.produtoValidate(item);
            }
        }
    }

    // valida quantidade em estoque UPDATE
    @SneakyThrows
    public void updateQuantidadeValidate(CarrinhoCompraRequest request){
        Set<ItemCarrinho> itensCarrinho = request.getItensCarrinho();

        for (ItemCarrinho item: itensCarrinho) {
            validationItemCarrinho.quantidadeValidate(item);
        }
    }

    // valida produto já existente no carrinho
    @SneakyThrows
    public void produtoCarrinhoValidate(CarrinhoCompra carrinhoCompra, ItemCarrinho itemCarrinho){
        if (itemCarrinhoRepository.selectItemByCarrinhoAndProduto(carrinhoCompra.getId(), itemCarrinho.getProduto().getId()) != null){
            throw new CustomValidationException("Produto id: " +itemCarrinho.getProduto().getId() + " já cadastrado no Carrinho de Compra id: " + carrinhoCompra.getId());
        }
    }

    public void allValidates(CarrinhoCompra carrinhoCompra) {
        clienteValidate(carrinhoCompra);
        produtosValidate(carrinhoCompra);
        quantidadeValidate(carrinhoCompra);
    }

    public void updateAllValidates(CarrinhoCompraRequest request) {
        updateProdutosValidate(request);
        updateQuantidadeValidate(request);
    }
}
