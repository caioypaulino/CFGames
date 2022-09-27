package com.project.cfgames.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.project.cfgames.entities.relations.EnderecoCliente;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.util.Set;


@Table(name = "CLIENTES")
@Entity(name = "Cliente")

@Getter
@Setter
@NoArgsConstructor

@JsonIdentityInfo(
        scope = Cliente.class,
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "clienteId")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clienteId;

    private String nome;
    private String cpf;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataNascimento;

    private String telefone;

    @Email(message = "Email should be valid")
    private String email;
    private String senha;

    @OneToMany(mappedBy = "cliente")
    private Set<EnderecoCliente> possuem;

    @ManyToMany
    @JoinTable(name = "detem", joinColumns = @JoinColumn(name = "cliente_id"), inverseJoinColumns = @JoinColumn(name = "numero_cartao"))
    private Set<Cartao> cartoes;

    public Cliente(Long clienteId, String nome, String cpf, LocalDate dataNascimento, String telefone, String email, String senha, Set<EnderecoCliente> possuem, Set<Cartao> cartoes) {
        this.clienteId = clienteId;
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.email = email;
        this.senha = senha;
        this.possuem = possuem;
        this.cartoes = cartoes;
    }

    public Set<Cartao> getCartoes() {
        return cartoes;
    }

    public void cartoesCliente(Cartao cartao) {
        cartoes.add(cartao);
    }
}