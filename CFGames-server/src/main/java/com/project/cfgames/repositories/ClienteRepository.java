package com.project.cfgames.repositories;

import com.project.cfgames.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Cliente findByEmail(String email);

    @Transactional
    @Query(value = "SELECT * FROM public.clientes WHERE cliente_id != ? AND email = ? ", nativeQuery = true)
    Cliente findByEmailUpdate(Long id, String email);
}