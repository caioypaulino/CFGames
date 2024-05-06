package com.project.cfgames.dtos.responses;

import com.project.cfgames.entities.Produto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GraficoProdutoResponse {
    private Produto produto;
    private List<StatsResponse> stats;
}