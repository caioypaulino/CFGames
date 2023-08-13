package com.project.cfgames.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class ClienteResponse {

    @JsonProperty
    private Long id;

    @JsonProperty
    private String nome;

    @JsonProperty
    private String cpf;

    @JsonProperty
    private LocalDate dataNascimento;

    @JsonProperty
    private String telefone;

    @JsonProperty
    private String email;

    public ClienteResponse() {}

    public ClienteResponse(Long id, String nome, String cpf, LocalDate dataNascimento, String telefone, String email) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.email = email;
    }

}
