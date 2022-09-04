package com.project.cfgames.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.project.cfgames.dao.ClienteDao;
import com.project.cfgames.entities.Cliente;

import org.springframework.stereotype.Repository;

@Repository
public class ClienteDaoImpl implements ClienteDao{

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void create(Cliente cliente){
        entityManager.createNativeQuery("INSERT INTO cliente (cpf, data_nascimento, email, nome, senha, telefone) VALUES (?,?,?,?,?,?)")
        .setParameter(1, cliente.getCpf())
        .setParameter(2, cliente.getDataNascimento())
        .setParameter(3, cliente.getEmail())
        .setParameter(4, cliente.getNome())
        .setParameter(5, cliente.getSenha())
        .setParameter(6, cliente.getTelefone())
        .executeUpdate();
    }

   
}