package com.project.cfgames.repositories;


import com.project.cfgames.clients.entities.ItemCarrinho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface ItemCarrinhoRepository extends JpaRepository<ItemCarrinho, Long> {
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM public.itens_carrinho WHERE item_carrinho_id = ? AND carrinho_compra_id = ?", nativeQuery = true)
    void removeItem(Long itemCarrinhoId, Long carrinhoCompraId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM public.itens_carrinho WHERE carrinho_compra_id = ?", nativeQuery = true)
    void deleteItens(Long carrinhoCompraId);
}
