package com.project.cfgames.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CupomRequest {
    @NotNull(message = "Campo não informado!")
    private Float valorDesconto;

    @NotNull(message = "Campo não informado!")
    private Long clienteId;
}
