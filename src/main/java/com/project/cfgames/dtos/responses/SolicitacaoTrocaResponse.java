package com.project.cfgames.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.project.cfgames.entities.SolicitacaoTroca;
import lombok.Data;

@Data
@JsonPropertyOrder({"id", "cliente_id", "pedido", "cliente", "itensTroca", "data", "dataAtualizacao", "observacao", "status"})
public class SolicitacaoTrocaResponse extends SolicitacaoTroca {
    @JsonProperty("cliente_id")
    public Long getClienteId() {
        return super.getCliente().getId();
    }
}
