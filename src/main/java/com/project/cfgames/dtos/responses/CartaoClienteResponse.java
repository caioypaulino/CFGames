package com.project.cfgames.dtos.responses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.cfgames.entities.Cliente;
import com.project.cfgames.entities.Endereco;
import com.project.cfgames.entities.enums.BandeiraCartao;
import com.project.cfgames.entities.enums.TipoEndereco;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartaoClienteResponse {
    private String numeroCartao;
    private String nomeCartao;
    private BandeiraCartao bandeira;
    private Integer mesVencimento;
    private Integer anoVencimento;
    private String cvc;
    @JsonIgnore
    private Cliente cliente;
}
