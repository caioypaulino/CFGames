package com.project.cfgames.services;

import com.project.cfgames.entities.templates.TemplateEndereco;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EnderecoService {

    public TemplateEndereco buscarCep(String cep){
        try {
            RestTemplate restTemplate = new RestTemplate();

            return restTemplate.getForObject("https://cdn.apicep.com/file/apicep/" + cep + ".json", TemplateEndereco.class);
        }
        catch (RuntimeException exception){
            throw new RuntimeException("Cep inválido ou inexistente");
        }
    }
}
