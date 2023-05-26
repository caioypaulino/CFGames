package com.project.cfgames.configs;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.project.cfgames.entities.Cliente;
import com.project.cfgames.exceptions.CustomValidationException;
import com.project.cfgames.repositories.ClienteRepository;
import com.project.cfgames.services.TokenService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class FilterToken extends OncePerRequestFilter {
    @Autowired
    TokenService tokenService;
    @Autowired
    ClienteRepository clienteRepository;

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try {
            String authorizationHeader = request.getHeader("Authorization");

            if (authorizationHeader != null) {
                String token = authorizationHeader.replace("Bearer ", "");

                String subject = tokenService.getSubject(token);

                Cliente cliente = clienteRepository.findByEmail(subject);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(cliente, null, cliente.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(request, response);
        }
        catch (JWTVerificationException ex) {
            throw new CustomValidationException("Token Inválido");
        }
    }
}
