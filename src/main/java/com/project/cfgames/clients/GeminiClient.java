package com.project.cfgames.clients;

import com.project.cfgames.clients.requests.GeminiRequest;
import com.project.cfgames.clients.responses.GeminiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

// consumindo API externa através de FeignClient e atribuindo ao ResponseDTO (FreteResponse)
@FeignClient(name = "GeminiCliente", url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=AIzaSyBKLgiR9ArskrSlOoDWuC_goZIm5MrEZFE")
public interface GeminiClient {
    @PostMapping()
    GeminiResponse getResponse(@RequestBody GeminiRequest request);
}