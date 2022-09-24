package com.project.cfgames.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;


@Table(name = "ENDERECOS")
@Entity(name = "Endereco")

@Getter
@Setter
@NoArgsConstructor

@JsonIdentityInfo(
        scope = Endereco.class,
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "enderecoId")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long enderecoId;

    private String rua;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    private String pais;

    public Endereco(String rua, String bairro, String cidade, String estado, String cep, String pais) {
        this.rua = rua;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
        this.pais = pais;
    }

}