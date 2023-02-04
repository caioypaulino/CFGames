package com.project.cfgames.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.project.cfgames.entities.enums.StatusProduto;
import com.project.cfgames.entities.enums.Plataforma;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Table(name = "PRODUTOS")
@Entity(name = "Produto")

@Getter
@Setter
@NoArgsConstructor

@JsonIdentityInfo(
        scope = Produto.class,
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "produto_id")
    private Long id;

    private String titulo;
    private String descricao;

    //enum
    private Plataforma plataforma;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataLancamento;
    private String marca;
    private String publisher;

    // peso em gramas
    private Integer peso;

    private Float comprimento;

    private Float altura;

    private Float largura;

    private String codigoBarras;
    private Integer quantidade;
    private Float preco;
    private StatusProduto status;

    @ManyToMany
    @JoinTable(name = "categorias_produtos", joinColumns = @JoinColumn(name = "produto_id"), inverseJoinColumns = @JoinColumn(name = "categoria_id"))
    private Set<Categoria> categorias;

    public Produto(String titulo, String descricao, Plataforma plataforma, LocalDate dataLancamento, String marca, String publisher, Integer peso, Float comprimento, Float altura, Float largura, String codigoBarras, Integer quantidade, Float preco, StatusProduto status, Set<Categoria> categorias) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.plataforma = plataforma;
        this.dataLancamento = dataLancamento;
        this.marca = marca;
        this.publisher = publisher;
        this.peso = peso;
        this.comprimento = comprimento;
        this.altura = altura;
        this.largura = largura;
        this.codigoBarras = codigoBarras;
        this.quantidade = quantidade;
        this.preco = preco;
        this.status = status;
        this.categorias = categorias;
    }

    public Set<Categoria> getCategorias() {
        return categorias;
    }
    public void categoriasProduto(Categoria categoria) {
        categorias.add(categoria);
    }

}
