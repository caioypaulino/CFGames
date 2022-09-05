package com.project.cfgames.entities;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

import java.util.Date;


@Table(name = "Cliente")
@Entity(name = "Cliente")

@NoArgsConstructor
@AllArgsConstructor
@Data

public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String nome;
    private Integer cpf;

    @JsonFormat(pattern="dd-MM-yyyy")
    private Date dataNascimento;

    private Integer telefone;
    private String email;
    private String senha;
    
}