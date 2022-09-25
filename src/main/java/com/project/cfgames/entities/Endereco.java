package com.project.cfgames.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.project.cfgames.entities.templates.TemplateEndereco;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Table(name = "ENDERECOS")
@Entity(name = "Endereco")

@Getter
@Setter
@NoArgsConstructor

@JsonIdentityInfo(
        scope = Endereco.class,
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "cep")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Endereco {
    @Id
    private String cep;
    private String estado;
    private String cidade;
    private String bairro;
    private String rua;
    private String pais;

    public Endereco(String rua, String bairro, String cidade, String estado, String cep, String pais) {
        this.rua = rua;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
        this.pais = pais;
    }

    public Endereco(TemplateEndereco templateEndereco, String pais) {
        this.cep = templateEndereco.getCode();
        this.rua = templateEndereco.getAddress();
        this.bairro = templateEndereco.getDistrict();
        this.cidade = templateEndereco.getCity();
        this.estado = templateEndereco.getState();
        this.pais = pais;
    }
}