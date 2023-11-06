package com.project.cfgames.dtos.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GraficoRequest {
    private boolean produto;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime dataInicio;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime dataFim;
}