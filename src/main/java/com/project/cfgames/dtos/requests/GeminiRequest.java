package com.project.cfgames.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeminiRequest {
    @NotBlank(message = "Campo não informado, por favor insira o nome de um jogo!")
    String nomeJogo;
}
