package com.project.cfgames.dtos.requests;

import com.project.cfgames.entities.Endereco;
import com.project.cfgames.entities.enums.TipoEndereco;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoClienteRequest {
    private String apelido;
    @Pattern(regexp = "^[0-9]+$", message = "Número inválido.")
    private String numero;
    private String complemento;
    private TipoEndereco tipo;
    private Endereco endereco;
    private String observacao;
}
