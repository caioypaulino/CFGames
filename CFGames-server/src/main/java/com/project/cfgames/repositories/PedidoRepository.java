package com.project.cfgames.repositories;

import com.project.cfgames.entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    @Transactional
    @Query(value = "SELECT pedido_id FROM public.pedidos WHERE carrinho_compra_carrinho_compra_id = ?", nativeQuery = true)
    Long selectPedidoByCarrinho(Long carrinhoCompraId);
}
