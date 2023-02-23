package com.project.cfgames.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.project.cfgames.entities.enums.Plataforma;
import com.project.cfgames.entities.enums.StatusProduto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
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

    @NotBlank(message = "Campo não informado!")
    private String titulo;
    @NotBlank(message = "Campo não informado!")
    private String descricao;
    @NotNull(message = "Campo não informado!")
    // enum
    private Plataforma plataforma;
    @NotNull(message = "Campo não informado!")
    @PastOrPresent(message = "Data de lançamento inválida.")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dataLancamento;
    @NotBlank(message = "Campo não informado!")
    private String marca;
    @NotBlank(message = "Campo não informado!")
    private String publisher;
    @NotNull(message = "Campo não informado!")
    @Min(value = 1, message = "Peso inválido.")
    // peso em gramas
    private Integer peso;
    @NotNull(message = "Campo não informado!")
    @DecimalMin(value = ".1", message = "Comprimento inválido.")
    private Float comprimento;
    @NotNull(message = "Campo não informado!")
    @DecimalMin(value = ".1", message = "Altura inválida.")
    private Float altura;
    @NotNull(message = "Campo não informado!")
    @DecimalMin(value = ".1", message = "Largura inválida.")
    private Float largura;
    @NotBlank(message = "Campo não informado!")
    @Pattern(regexp = "^[0-9]{13}$", message = "Código de barras inválido (necessário 13 dígitos e apenas números).")
    private String codigoBarras;
    @NotNull(message = "Campo não informado!")
    @Min(value = 1, message = "Quantidade inválida.")
    private Integer quantidade;
    @NotNull(message = "Campo não informado!")
    @DecimalMin(value = "10.0", message = "Preço inválido (mínimo R$10,00).")
    private Float preco;
    @NotNull(message = "Campo não informado!")
    // enum
    private StatusProduto status;

    @NotEmpty(message = "Campo não informado!")
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
    public void addCategoriasProduto(Categoria categoria) {
        categorias.add(categoria);
    }

}
