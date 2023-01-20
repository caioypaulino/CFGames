package com.project.cfgames.clients;

import com.project.cfgames.clients.responses.FreteResponse;
import com.project.cfgames.clients.configs.FeignClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// consumindo API externa através de FeignClient e atribuindo ao ResponseDTO (FreteResponse)
@FeignClient(name = "FreteClient", url = "https://www.cepcerto.com/ws/json-frete/08773600/", configuration = FeignClientConfiguration.class)
public interface FreteClient {
    @GetMapping(value = "/{cepDestino}/{peso}")
    FreteResponse getFrete(@PathVariable("cepDestino") String cepDestino, @PathVariable("peso") Integer peso);
}
