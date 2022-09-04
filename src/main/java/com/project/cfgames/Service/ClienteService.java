package com.project.cfgames.Service;
import com.project.cfgames.entity.Cliente;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {
    
    //@Autowired
    //private ClienteRepository clienteRepository

    public List<Cliente> getClienteList() {
        return null;
    }

    /*
    public List<Cliente> getClienteList() {
        Iterable<Cliente> clienteIterable = this.authorRepository.findAll();
        return Streamble.of(clienteIterable).toList();


    }
     */
}
