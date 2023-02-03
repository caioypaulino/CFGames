package com.project.cfgames.repositories;

import com.project.cfgames.clients.entities.relations.CartaoPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface CartaoPedidoRepository extends JpaRepository<CartaoPedido, Long> {
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM public.cartoes_pedidos WHERE pedido_pedido_id = ?", nativeQuery = true)
    void deleteCartoes(Long pedidoId);
}
