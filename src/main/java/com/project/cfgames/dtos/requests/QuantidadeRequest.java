package com.project.cfgames.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuantidadeRequest {
    @Digits(integer = Integer.MAX_VALUE, fraction = 0, message = "A quantidade deve ser um número inteiro.")
    private Integer quantidade;
}
