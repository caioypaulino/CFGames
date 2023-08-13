package com.project.cfgames.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoRequest {
    private String estado;
    private String cidade;
    private String bairro;
    private String rua;
    private String pais;
}
