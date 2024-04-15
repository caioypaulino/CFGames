package com.project.cfgames.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.project.cfgames.entities.SolicitacaoTroca;
import lombok.Data;

@Data
@JsonPropertyOrder({"id", "cliente", "pedido", "itensTroca", "data", "dataAtualizacao", "observacao", "status"})
public class SolicitacaoTrocaResponse extends SolicitacaoTroca {
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
