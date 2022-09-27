package com.project.cfgames.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Table(name = "CARTOES")
@Entity(name = "Cartao")

@Getter
@Setter
@NoArgsConstructor

@JsonIdentityInfo(
        scope = Cartao.class,
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "numeroCartao")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Cartao {
    @Id
    private Long numeroCartao;

    private String nomeCartao;
    private String bandeira;
    private Integer cvc;

    @JsonIgnore
    @ManyToMany (mappedBy = "cartoes")
    Set<Cliente> clientes;

    public Cartao(Long numeroCartao, String nomeCartao, String bandeira, Integer cvc, Set<Cliente> clientes) {
        this.numeroCartao = numeroCartao;
        this.nomeCartao = nomeCartao;
        this.bandeira = bandeira;
        this.cvc = cvc;
        this.clientes = clientes;
    }
}
