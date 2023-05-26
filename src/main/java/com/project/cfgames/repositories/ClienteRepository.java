package com.project.cfgames.repositories;

import com.project.cfgames.entities.CarrinhoCompra;
import com.project.cfgames.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Cliente findByEmail(String email);
    @Transactional
    @Query(value = "SELECT * FROM public.clientes WHERE cliente_id != ? AND email = ? ", nativeQuery = true)
    Cliente findByEmailUpdate(Long id, String email);
    @Transactional
    @Query(value = "SELECT senha FROM public.clientes WHERE cliente_id = ?", nativeQuery = true)
    String findSenhaByCliente(Long id);

    @Transactional
    @Query(value = "SELECT * FROM public.clientes WHERE nome LIKE %?%", nativeQuery = true)
    Optional<List<Cliente>> findByNome(String nome);

    @Transactional
    @Query(value = "SELECT carrinhos_compra.carrinho_compra_id FROM public.clientes LEFT JOIN public.carrinhos_compra ON carrinhos_compra.cliente_cliente_id = clientes.cliente_id LEFT JOIN public.pedidos ON pedidos.carrinho_compra_carrinho_compra_id = carrinhos_compra.carrinho_compra_id WHERE clientes.cliente_id = ? AND pedidos.pedido_id ISNULL", nativeQuery = true)
    Long findCarrinhoAberto(Long id);
}