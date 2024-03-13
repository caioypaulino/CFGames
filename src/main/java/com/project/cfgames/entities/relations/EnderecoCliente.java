package com.project.cfgames.entities.relations;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.cfgames.dtos.requests.EnderecoClienteRequest;
import com.project.cfgames.entities.Cliente;
import com.project.cfgames.entities.Endereco;
import com.project.cfgames.entities.enums.TipoEndereco;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Table(name = "ENDERECOS_CLIENTES")
@Entity(name = "EnderecoCliente")

@Getter
@Setter
@NoArgsConstructor

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class EnderecoCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name =  "endereco_cliente_id")
    private Long id;

    @NotNull(message = "Campo não informado!")
    @Pattern(regexp = "^[0-9]+$", message = "Número inválido.")
    private String numero;
    @NotBlank(message = "Campo não informado!")
    private String complemento;
    @NotNull(message = "Campo não informado!")
    private TipoEndereco tipo;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY)
    private Cliente cliente;

    @Valid
    @NotNull(message = "Campo não informado!")
    @ManyToOne(fetch = FetchType.LAZY)
    private Endereco endereco;

    private String observacao;

    public EnderecoCliente(String numero, String complemento, TipoEndereco tipo, Cliente cliente, Endereco endereco) {
        this.numero = numero;
        this.complemento = complemento;
        this.tipo = tipo;
        this.cliente = cliente;
        this.endereco = endereco;
    }

    public EnderecoCliente(Long id, EnderecoClienteRequest enderecoClienteRequest, Cliente cliente){
        this.id = id;
        this.numero = enderecoClienteRequest.getNumero();
        this.complemento = enderecoClienteRequest.getComplemento();
        this.tipo = enderecoClienteRequest.getTipo();
        this.cliente = cliente;
        this.endereco = enderecoClienteRequest.getEndereco();
    }
}
