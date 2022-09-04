/*
package com.project.cfgames.entity;

import javax.persistence.*;
import java.util.Date;

@Table(name = "Cliente")
@Entity(name = "Cliente")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private String nome;
    private Integer cpf;
    private Date dataNascimento;
    private Long telefone;
    private String email;
    private String senha;

    public Cliente() {
    }

    public Cliente(String nome, Integer cpf, Date dataNascimento, Long telefone, String email, String senha) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.email = email;
        this.senha = senha;
    }

    public Long getId() {
        return id;
    }
}
*/