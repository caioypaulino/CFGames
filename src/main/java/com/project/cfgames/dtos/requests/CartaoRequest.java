package com.project.cfgames.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartaoRequest {
    @Pattern(regexp = "^[A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ ]+$", message = "Nome do cartão não pode conter números ou caractéres especiais.")
    private String nomeCartao;
    @Min(value = 1, message = "Mês Vencimento inválido.")
    @Max(value = 12, message = "Mês Vencimento inválido.")
    private Integer mesVencimento;
    @Min(value = 2023, message = "Ano Vencimento inválido.")
    private Integer anoVencimento;
    @Pattern(regexp = "^[0-9]{3,4}$", message = "CVC inválido.")
    private String cvc;
}
