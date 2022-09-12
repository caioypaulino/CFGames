package com.project.cfgames.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Table(name = "ENDERECOS")
@Entity(name = "Endereco")

@NoArgsConstructor
@AllArgsConstructor
@Data

public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String rua;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    private String pais;

}