package com.project.cfgames.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.project.cfgames.entities.relations.EnderecoCliente;
import com.project.cfgames.responses.EnderecoDTO;
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

    @JsonIgnore
    @OneToMany(mappedBy = "endereco")
    Set<EnderecoCliente> clientes;

    public Endereco(String rua, String bairro, String cidade, String estado, String cep, String pais) {
        this.rua = rua;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
        this.pais = pais;
    }

    public Endereco(EnderecoDTO enderecoDTO, String pais) {
        this.cep = enderecoDTO.getCode();
        this.rua = enderecoDTO.getAddress();
        this.bairro = enderecoDTO.getDistrict();
        this.cidade = enderecoDTO.getCity();
        this.estado = enderecoDTO.getState();
        this.pais = pais;
    }
}