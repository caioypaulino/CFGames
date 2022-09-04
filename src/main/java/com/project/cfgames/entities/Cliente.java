package com.project.cfgames.entities;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Date dataNascimento;
    private Long telefone;
    private String email;
    private String senha;
    
}