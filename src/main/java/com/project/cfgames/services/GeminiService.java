package com.project.cfgames.services;

import com.project.cfgames.clients.GeminiClient;
import com.project.cfgames.clients.requests.ContentRequest;
import com.project.cfgames.clients.requests.GeminiRequest;
import com.project.cfgames.clients.requests.PartRequest;
import com.project.cfgames.clients.responses.GeminiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GeminiService {
    @Autowired
    GeminiClient geminiClient;

    // definindo todos os parâmetros de Gemini Request para gerar resposta através da Gemini IA
    private static GeminiRequest setGeminiRequest(String nomeJogo) {
        GeminiRequest request = new GeminiRequest();
        ContentRequest content = new ContentRequest();
        PartRequest part = new PartRequest();

        part.setText("Retorne Curiosidades/Descrição/Sinopse Simples sobre o jogo: '" + nomeJogo + "'. " +
                     "Limite a resposta a no máximo 50 palavras. " +
                     "(Caso não seja o nome de um jogo de videogame, retorne que o nome está incorreto)");

        List<PartRequest> parts = new ArrayList<>();
        parts.add(part);

        content.setParts(parts);

        List<ContentRequest> contents = new ArrayList<>();
        contents.add(content);

        request.setContents(contents);


        return request;
    }

    // gera resposta através da feign client api (GeminiClient) e retorna uma responseDTO (GeminiResponse)
    public GeminiResponse gerarResposta(String nomeJogo) {
        GeminiRequest request = setGeminiRequest(nomeJogo);

        return geminiClient.getResponse(request);
    }
}
