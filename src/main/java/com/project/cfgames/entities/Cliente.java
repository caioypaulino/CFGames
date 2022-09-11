package com.project.cfgames.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Table(name = "CLIENTES")
@Entity(name = "Cliente")

@NoArgsConstructor
@AllArgsConstructor
@Data

public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String nome;
    private String cpf;

    @JsonFormat(pattern="dd-MM-yyyy")
    private Date dataNascimento;

    private String telefone;
    private String email;
    private String senha;
    
}