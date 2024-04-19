package com.project.cfgames.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.project.cfgames.entities.Cupom;
import lombok.Data;

@Data
@JsonPropertyOrder({"codigoCupom", "valorDesconto", "data", "validade", "disponivel", "cliente"})
public class CupomResponse extends Cupom {
    @JsonProperty("cliente")
    public ClienteResponseAdmin getClienteDTO() {
        ClienteResponseAdmin cliente = new ClienteResponseAdmin();
        cliente.setId(super.getCliente().getId());
        cliente.setNome(super.getCliente().getNome());
        cliente.setCpf(super.getCliente().getCpf());
        cliente.setDataNascimento(super.getCliente().getDataNascimento());
        cliente.setTelefone(super.getCliente().getTelefone());
        cliente.setEmail(super.getCliente().getEmail());

        return cliente;
    }
}

