package com.project.cfgames.repositories;

import com.project.cfgames.entities.Pedido;
import com.project.cfgames.entities.enums.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    @Transactional
    @Query(value = "SELECT pedido_id FROM public.pedidos WHERE carrinho_compra_carrinho_compra_id = ?", nativeQuery = true)
    Long selectPedidoByCarrinho(Long carrinhoCompraId);
    @Transactional
    @Query(value = "SELECT * FROM public.pedidos WHERE cliente_cliente_id = ?", nativeQuery = true)
    List<Pedido> selectPedidosByCliente(Long clienteId);
    @Transactional
    @Query(value = "SELECT endereco_cliente_endereco_cliente_id FROM public.pedidos WHERE endereco_cliente_endereco_cliente_id = ?", nativeQuery = true)
    List<Long> selectPedidosByEndereco(Long enderecoClienteId);

    List<Pedido> findByStatus(StatusPedido statusPedido);
}
