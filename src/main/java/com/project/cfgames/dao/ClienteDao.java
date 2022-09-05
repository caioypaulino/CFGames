package com.project.cfgames.dao;

import com.project.cfgames.entities.Cliente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteDao extends JpaRepository <Cliente, Long>{


}