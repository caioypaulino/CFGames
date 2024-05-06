package com.project.cfgames.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatsResponse {
    private Integer quantidadeVendida;
    private Float valorTotal;
    private Integer mes;
    private Integer ano;
}
