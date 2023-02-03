package com.project.cfgames.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.project.cfgames.entities.enums.BandeiraCartao;
import com.project.cfgames.entities.relations.CartaoPedido;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.persistence.*;
import javax.validation.constraints.*;
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
    @NotBlank(message = "Campo não informado!")
    @CreditCardNumber(message = "Número cartão inválido.")
    private String numeroCartao;
    @NotBlank(message = "Campo não informado!")
    @Pattern(regexp = "^[A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ ]+$", message = "Nome do cartão não pode conter números ou caractéres especiais.")
    private String nomeCartao;
    private BandeiraCartao bandeira;
    @NotNull(message = "Campo não informado!")
    @Min(value = 1, message = "Mês Vencimento inválido.")
    @Max(value = 12, message = "Mês Vencimento inválido.")
    private Integer mesVencimento;
    @NotNull(message = "Campo não informado!")
    @Min(value = 2023, message = "Ano Vencimento inválido.")
    private Integer anoVencimento;
    @NotBlank(message = "Campo não informado!")
    @Pattern(regexp = "^[0-9]{3,4}$", message = "CVC inválido.")
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
