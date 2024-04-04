package com.project.cfgames.clients;

import com.project.cfgames.clients.responses.FreteResponse;
import com.project.cfgames.clients.configs.FeignClientConfiguration;
import com.project.cfgames.clients.requests.FreteRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

// consumindo API externa através de FeignClient e atribuindo ao ResponseDTO (FreteResponse)
@FeignClient(name = "FreteClient", url = "https://www.melhorenvio.com.br/api/v2/me", configuration = FeignClientConfiguration.class)
public interface FreteClient {
    @PostMapping("/shipment/calculate")
    FreteResponse getFrete(@RequestBody FreteRequest request, @RequestHeader("Authorization") String authorization, @RequestHeader("User-Agent") String userAgent);
}
