package com.project.cfgames.builder;

import com.project.cfgames.entities.Cartao;
import com.project.cfgames.entities.Cliente;
import com.project.cfgames.entities.relations.EnderecoCliente;

import java.time.LocalDate;
import java.util.Set;

public class ClienteBuilder {

    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private String telefone;
    private String email;
    private String senha;
    private Set<EnderecoCliente> possuem;
    private Set<Cartao> cartoes;

    public ClienteBuilder addNome(String nome) {
        this.nome = nome;
        return this;
    }

    public ClienteBuilder addCpf(String cpf) {
        this.cpf = cpf;
        return this;
    }

    public ClienteBuilder addDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
        return this;
    }

    private ClienteBuilder addTelefone(String telefone) {
        this.telefone = telefone;
        return this;
    }

    private ClienteBuilder addEmail(String email) {
        this.email = email;
        return this;
    }

    private ClienteBuilder addSenha(String senha) {
        this.senha = senha;
        return this;
    }

    public ClienteBuilder addPossuem(Set<EnderecoCliente> possuem) {
        this.possuem = possuem;
        return this;
    }

    public ClienteBuilder addCartoes(Set<Cartao> cartoes) {
        this.cartoes = cartoes;
        return this;
    }

    public Cliente build() {
        if (nome == null || cpf == null || dataNascimento == null || telefone == null || email == null || senha == null || possuem == null)
            throw new IllegalArgumentException("Cliente inválido!");
        return new Cliente(nome, cpf, dataNascimento, telefone, email, senha, possuem, cartoes);
    }

}
