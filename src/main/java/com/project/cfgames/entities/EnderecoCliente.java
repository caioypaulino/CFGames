package com.project.cfgames.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.project.cfgames.entities.enums.TipoEndereco;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Table(name = "Possuem")
@Entity(name = "Possuem")

@NoArgsConstructor
@AllArgsConstructor
@Data

public class EnderecoCliente {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Integer numero;
    private String complemento;

    private TipoEndereco tipo;

    @ManyToMany
    private List<Cliente> clienteList;

    @ManyToMany
    private List<Endereco> enderecoList;
}