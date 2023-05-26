package com.project.cfgames.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.project.cfgames.entities.Pedido;
import lombok.Data;

@Data
@JsonPropertyOrder({"id", "cliente_id","carrinhoCompra", "cliente", "enderecoCliente", "cartoes", "cupom", "data", "dataAtualizacao", "status", "frete", "valorTotal"})
public class PedidoResponse extends Pedido {
    @JsonProperty("cliente_id")
    public Long getClienteId() {
        return super.getCliente().getId();
    }
}
