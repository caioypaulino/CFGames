package com.project.cfgames.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.project.cfgames.entities.Pedido;
import lombok.Data;

@Data
@JsonPropertyOrder({"id", "cliente", "carrinhoCompra", "cliente", "enderecoCliente", "cartoes", "cupom", "data", "dataAtualizacao", "status", "frete", "valorTotal"})
public class PedidoResponse extends Pedido {
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
