package com.project.cfgames.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemCarrinhoRequest {
    @NotNull(message = "Campo não informado!")
    private Long itemCarrinhoId;
    @NotNull(message = "Campo não informado!")
    private Long produtoId;
    @NotNull(message = "Campo não informado!")
    @Min(value = 1, message = "Quantidade inválida.")
    private Integer quantidade;

}
