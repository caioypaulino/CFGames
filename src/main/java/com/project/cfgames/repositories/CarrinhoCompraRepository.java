package com.project.cfgames.repositories;


import com.project.cfgames.entities.CarrinhoCompra;
import com.project.cfgames.entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface CarrinhoCompraRepository extends JpaRepository<CarrinhoCompra, Long> {

    @Transactional
    @Query(value = "SELECT pedido_id FROM public.pedidos WHERE carrinho_compra_carrinho_compra_id = ?", nativeQuery = true)
    Long selectPedidoId(Long carrinhoCompraId);
}
