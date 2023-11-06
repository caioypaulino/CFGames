package com.project.cfgames.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GraficoResponse {
    private String nome;
    private Integer quantidadeVendida;
    private Float valorTotal;
}
