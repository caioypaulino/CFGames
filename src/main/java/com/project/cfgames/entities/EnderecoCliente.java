package com.project.cfgames.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.project.cfgames.entities.enums.TipoEndereco;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "Possuem")
@Entity(name = "Possuem")

@Getter
@Setter
@NoArgsConstructor

@JsonIdentityInfo(
        scope = EnderecoCliente.class,
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "possuemId")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class EnderecoCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long possuemId;

    private Integer numero;
    private String complemento;

    private TipoEndereco tipo;

    @ManyToOne(fetch = FetchType.LAZY)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    private Endereco endereco;

    public EnderecoCliente(Integer numero, String complemento, TipoEndereco tipo, Cliente cliente, Endereco endereco) {
        this.numero = numero;
        this.complemento = complemento;
        this.tipo = tipo;
        this.cliente = cliente;
        this.endereco = endereco;
    }

}
