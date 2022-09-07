package com.project.cfgames.dao;

import com.project.cfgames.entities.Endereco;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoDao extends JpaRepository<Endereco, Long> {
                    
}