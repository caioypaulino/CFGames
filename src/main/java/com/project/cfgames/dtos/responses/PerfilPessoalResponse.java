package com.project.cfgames.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PerfilPessoalResponse {
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private String telefone;
}
