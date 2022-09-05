package com.project.cfgames.repository;

import org.springframework.data.repository.CrudRepository;

import com.project.cfgames.entities.Cliente;

public interface ClienteRepository extends CrudRepository<Cliente, Long>{
    
}
