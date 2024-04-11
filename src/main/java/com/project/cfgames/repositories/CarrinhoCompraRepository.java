package com.project.cfgames.repositories;


import com.project.cfgames.entities.CarrinhoCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface CarrinhoCompraRepository extends JpaRepository<CarrinhoCompra, Long> {

    @Transactional
    @Query(value = "SELECT carrinho_compra_carrinho_compra_id FROM pedidos WHERE pedido_id = ?", nativeQuery = true)
    Long selectCarrinhoPedido(Long pedidoId);
}
