package com.project.cfgames.services;

import com.project.cfgames.clients.EnderecoClient;
import com.project.cfgames.clients.responses.EnderecoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService {

    @Autowired
    EnderecoClient enderecoClient;

    // retorna endereco através da feign client api (EnderecoClient) e gera uma responseDTO (EnderecoResponse)
    public EnderecoResponse buscarCep(String cep){
        return enderecoClient.getEndereco(cep);
    }
}

