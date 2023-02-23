package com.project.cfgames.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CarrinhoCompra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "carrinho_compra_id")
    private Long id;

    @NotNull(message = "Campo não informado!")
    @ManyToOne
    private Cliente cliente;

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
