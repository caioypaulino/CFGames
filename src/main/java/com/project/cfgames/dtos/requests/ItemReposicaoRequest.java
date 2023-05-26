package com.project.cfgames.dtos.requests;

import com.project.cfgames.entities.ItemTroca;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemReposicaoRequest {
    @NotNull(message = "Campo não informado!")
    private ItemTroca itemTroca;

    @NotNull(message = "Campo não informado!")
    @Min(value = 1, message = "Quantidade inválida.")
    private Integer quantidade;
}
