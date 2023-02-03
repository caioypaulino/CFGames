package com.project.cfgames.dtos.requests;

import com.project.cfgames.clients.entities.Endereco;
import com.project.cfgames.clients.entities.enums.TipoEndereco;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoClienteRequest {
    @Pattern(regexp = "^[0-9]+$", message = "Número inválido.")
    private String numero;
    private String complemento;
    private TipoEndereco tipo;
    private Endereco endereco;
}
