package com.project.cfgames.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.cfgames.entities.CarrinhoCompra;
import com.project.cfgames.entities.ItemCarrinho;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
public class CarrinhoCompraResponse extends CarrinhoCompra {
    @JsonProperty("cliente_id")
    public Long getClienteId() {
        return super.getCliente().getId();
    }
}
