package com.project.cfgames.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Table(name = "CATEGORIAS")
@Entity(name = "Categoria")

@Getter
@Setter
@NoArgsConstructor

@JsonIdentityInfo(
        scope = Categoria.class,
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "categoriaId")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoriaId;

    private String nome;

    @JsonIgnore
    @ManyToMany (mappedBy = "categorias")
    Set<Produto> produtos;

    public Categoria(Long categoriaId, String nome, Set<Produto> produtos) {
        this.categoriaId = categoriaId;
        this.nome = nome;
        this.produtos = produtos;
    }
}
