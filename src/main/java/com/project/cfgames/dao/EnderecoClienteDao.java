package com.project.cfgames.dao;

import com.project.cfgames.entities.EnderecoCliente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoClienteDao extends JpaRepository<EnderecoCliente, Long> {
                    
}