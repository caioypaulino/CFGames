package com.project.cfgames.dtos.responses;

import com.project.cfgames.entities.enums.Genero;
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
    private Genero genero;
    private String telefone;
}
