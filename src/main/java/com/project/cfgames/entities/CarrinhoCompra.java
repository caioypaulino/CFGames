package com.project.cfgames.entities;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Table(name = "CARRINHOS_COMPRA")
@Entity(name = "CarrinhoCompra")

@Getter
@Setter
@ToString
@NoArgsConstructor

@JsonIdentityInfo(
        scope = CarrinhoCompra.class,
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "itens"})
@JsonPropertyOrder({"id", "cliente", "pesoTotal", "valorCarrinho", "itensCarrinho"})
public class CarrinhoCompra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "carrinho_compra_id")
    private Long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    private Cliente cliente;

    @Valid
    @NotNull(message = "Campo não informado!")
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "carrinho_compra_id", referencedColumnName = "carrinho_compra_id")
    private Set<ItemCarrinho> itensCarrinho;

    private Integer pesoTotal;

    private Float valorCarrinho;

    public CarrinhoCompra(Cliente cliente) {
        this.cliente = cliente;
    }

    public CarrinhoCompra(Cliente cliente, @Valid Set<ItemCarrinho> itensCarrinho) {
        this.cliente = cliente;
        this.itensCarrinho = itensCarrinho;
    }

    public Set<ItemCarrinho> getItens() {
        return itensCarrinho;
    }

    public void addItensCarrinho(ItemCarrinho itemCarrinho) {
        itensCarrinho.add(itemCarrinho);
    }
}
