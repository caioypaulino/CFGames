package com.project.cfgames.repository;

import com.project.cfgames.entities.relations.EnderecoCliente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoClienteRepository extends JpaRepository<EnderecoCliente, Long> {
                    
}