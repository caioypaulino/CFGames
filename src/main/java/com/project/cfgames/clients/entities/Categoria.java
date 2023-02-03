package com.project.cfgames.clients.entities;

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
        property = "id")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categoria_id")
    private Long id;

    private String nome;

    @JsonIgnore
    @ManyToMany (mappedBy = "categorias")
    Set<Produto> produtos;

    public Categoria(Long id, String nome, Set<Produto> produtos) {
        this.id = id;
        this.nome = nome;
        this.produtos = produtos;
    }
}
