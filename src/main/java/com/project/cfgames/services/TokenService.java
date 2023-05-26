package com.project.cfgames.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.project.cfgames.entities.Cliente;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    // retorna JWT Token (Json Web Token) gerado a partir de um login Cliente
    public String gerarToken(Cliente cliente) {
        return JWT.create()
                .withIssuer("Produtos")
                .withSubject(cliente.getEmail())
                .withClaim("id", cliente.getId())
                .withExpiresAt(LocalDateTime.now().plusMinutes(120).toInstant(ZoneOffset.of("-03:00")))
                .sign(Algorithm.HMAC256("cfgames"));
    }

    public String getSubject(String token) {
        return JWT.require(Algorithm.HMAC256("cfgames"))
                .withIssuer("Produtos")
                .build().verify(token).getSubject();
    }
}
