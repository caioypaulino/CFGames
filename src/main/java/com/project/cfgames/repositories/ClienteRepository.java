package com.project.cfgames.repositories;

import com.project.cfgames.entities.Cliente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM public.detem WHERE cliente_id = ? AND numero_cartao = ?", nativeQuery = true)
    void removeCartao(Long clienteId, String numeroCartao);
}