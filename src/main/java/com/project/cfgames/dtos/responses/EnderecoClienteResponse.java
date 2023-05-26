package com.project.cfgames.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.project.cfgames.entities.relations.EnderecoCliente;
import lombok.Data;

@Data
@JsonPropertyOrder({"id", "numero", "complemento", "tipo", "cliente_id", "endereco"})
public class EnderecoClienteResponse extends EnderecoCliente {
    @JsonProperty("cliente_id")
    public Long getClienteId() {
        return super.getCliente().getId();
    }
}
