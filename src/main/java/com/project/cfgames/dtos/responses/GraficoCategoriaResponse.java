package com.project.cfgames.dtos.responses;

import com.project.cfgames.entities.Categoria;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GraficoCategoriaResponse {
    private Categoria categoria;
    private List<StatsResponse> stats;
}