package com.project.cfgames.services;

import com.project.cfgames.clients.GeminiClient;
import com.project.cfgames.clients.requests.ContentRequest;
import com.project.cfgames.clients.requests.GeminiRequest;
import com.project.cfgames.clients.requests.PartRequest;
import com.project.cfgames.clients.responses.FreteResponse;
import com.project.cfgames.clients.responses.GeminiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GeminiService {
    @Autowired
    GeminiClient geminiClient;

    // definindo todos os parâmetros de FreteRequest para cálculo de frete
    private static GeminiRequest setGeminiRequest(String nomeJogo) {
        GeminiRequest request = new GeminiRequest();
        ContentRequest content = new ContentRequest();
        PartRequest part = new PartRequest();

        part.setText("Retorne a Descrição/Sinopse Simples ou Curiosidades sobre o jogo: '" + nomeJogo + "'. Limite a resposta a no máximo 50 palavras.");

        List<PartRequest> parts = new ArrayList<>();
        parts.add(part);

        content.setParts(parts);

        List<ContentRequest> contents = new ArrayList<>();
        contents.add(content);

        request.setContents(contents);


        return request;
    }

    // calcula frete através da feign client api (FreteClient) e gera uma responseDTO (FreteResponse)
    public GeminiResponse gerarResposta(String nomeJogo) {
        GeminiRequest request = setGeminiRequest(nomeJogo);

        return geminiClient.getResponse(request);
    }
}
