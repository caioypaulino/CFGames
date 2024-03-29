package com.project.cfgames.repositories;

import com.project.cfgames.entities.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface CartaoRepository extends JpaRepository<Cartao, String> {
    @Transactional
    @Query(value = "SELECT numero_cartao FROM public.cartoes_clientes WHERE cliente_id = ? AND numero_cartao = ?", nativeQuery = true)
    String selectCartaoCliente(Long clienteId, String numeroCartao);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM public.cartoes_clientes WHERE cliente_id = ? AND numero_cartao = ?", nativeQuery = true)
    void removeCartao(Long clienteId, String numeroCartao);

    @Transactional
    @Query(value = "SELECT cliente_id FROM public.cartoes_clientes WHERE numero_cartao = ?", nativeQuery = true)
    String selectClientesCartao(String numeroCartao);

    @Transactional
    @Query(value = "SELECT pedido_pedido_id FROM public.cartoes_pedidos WHERE cartao_numero_cartao = ?", nativeQuery = true)
    String selectPedidosCartao(String numeroCartao);
}