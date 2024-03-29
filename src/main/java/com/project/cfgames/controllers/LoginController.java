package com.project.cfgames.controllers;

import com.project.cfgames.dtos.requests.LoginRequest;
import com.project.cfgames.dtos.responses.TokenResponse;
import com.project.cfgames.entities.Cliente;
import com.project.cfgames.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    TokenService tokenService;

    @PostMapping("/login")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest login) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(login.getEmail(), login.getSenha());

        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        Cliente cliente = (Cliente) authenticate.getPrincipal();

        TokenResponse token = new TokenResponse(tokenService.gerarToken(cliente));

        return ResponseEntity.ok().body(token);
    }
}
