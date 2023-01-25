package com.project.cfgames.clients;

import com.project.cfgames.clients.responses.EnderecoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// consumindo API externa através de FeignClient e atribuindo ao ResponseDTO (EnderecoResponse)
@FeignClient(name = "EnderecoClient", url = "https://cdn.apicep.com/file/apicep/")
public interface EnderecoClient {
    @GetMapping(value = "/{cep}" + ".json")
    EnderecoResponse getEndereco(@PathVariable("cep") String cep);
}
