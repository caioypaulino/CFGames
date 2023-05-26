package com.project.cfgames.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuantidadeRequest {
    @Min(value = 0, message = "Quantidade inválida.")
    private Integer quantidade;
}
