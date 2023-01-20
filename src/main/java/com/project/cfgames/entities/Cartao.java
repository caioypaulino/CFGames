package com.project.cfgames.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.project.cfgames.entities.enums.BandeiraCartao;
import com.project.cfgames.entities.relations.CartaoPedido;
import com.project.cfgames.entities.relations.EnderecoCliente;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
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
    @Column(name = "numero_cartao")
    private String numeroCartao;

    private String nomeCartao;
    private BandeiraCartao bandeira;


    private Integer mesVencimento;
    private Integer anoVencimento;
    private String cvc;

    @JsonIgnore
    @ManyToMany (mappedBy = "cartoes")
    Set<Cliente> clientes;

    @JsonIgnore
    @OneToMany(mappedBy = "cartao")
    private Set<CartaoPedido> pedidos;

    public Cartao(String numeroCartao, String nomeCartao, Integer mesVencimento, Integer anoVencimento, String cvc, Set<Cliente> clientes) {
        this.numeroCartao = numeroCartao;
        this.nomeCartao = nomeCartao;
        this.mesVencimento = mesVencimento;
        this.anoVencimento = anoVencimento;
        this.cvc = cvc;
        this.clientes = clientes;
    }

    public Cartao(String numeroCartao, String nomeCartao, BandeiraCartao bandeira, Integer mesVencimento, Integer anoVencimento, String cvc, Set<Cliente> clientes) {
        this.numeroCartao = numeroCartao;
        this.nomeCartao = nomeCartao;
        this.bandeira = bandeira;
        this.mesVencimento = mesVencimento;
        this.anoVencimento = anoVencimento;
        this.cvc = cvc;
        this.clientes = clientes;
    }
}
