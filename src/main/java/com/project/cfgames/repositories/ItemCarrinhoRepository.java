package com.project.cfgames.repositories;


import com.project.cfgames.entities.ItemCarrinho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface ItemCarrinhoRepository extends JpaRepository<ItemCarrinho, Long> {
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM public.itens_carrinhos WHERE item_carrinho_id = ? AND carrinho_compra_id = ?", nativeQuery = true)
    void removeItem(Long itemCarrinhoId, Long carrinhoCompraId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM public.itens_carrinhos WHERE carrinho_compra_id = ?", nativeQuery = true)
    void deleteItens(Long carrinhoCompraId);

    @Transactional
    @Query(value = "SELECT item_carrinho_id FROM public.itens_carrinhos WHERE carrinho_compra_id = ? AND produto_produto_id = ?", nativeQuery = true)
    Long selectItemByCarrinhoAndProduto(Long carrinhoCompraId, Long produtoId);

    @Transactional
    @Query(value = "SELECT item_carrinho_id FROM public.itens_carrinhos WHERE carrinho_compra_id = ? AND item_carrinho_id = ?", nativeQuery = true)
    Long selectItemByCarrinhoAndItem(Long carrinhoCompraId, Long itemId);
}
