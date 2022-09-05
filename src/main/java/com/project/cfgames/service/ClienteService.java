package com.project.cfgames.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.cfgames.entities.Cliente;
import com.project.cfgames.repository.ClienteRepository;

@Service
public class ClienteService {
    
    @Autowired
    private ClienteRepository clienteRepository;

    public void save(Cliente cliente){
        this.clienteRepository.save(cliente);
    }
}
