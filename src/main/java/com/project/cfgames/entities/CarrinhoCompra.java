package com.project.cfgames.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.project.cfgames.entities.enums.StatusPedido;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
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

    @OneToOne
    private Cliente cliente;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "carrinho_compra_id", referencedColumnName = "carrinho_compra_id")
    private Set<ItemCarrinho> itensCarrinho;

    private Float valorTotal;

    public CarrinhoCompra(Cliente cliente) {
        this.cliente = cliente;
    }

    public CarrinhoCompra(Cliente cliente, Set<ItemCarrinho> itensCarrinho) {
        this.cliente = cliente;
        this.itensCarrinho = itensCarrinho;
    }

    public Set<ItemCarrinho> getItens() {
        return itensCarrinho;
    }

    public void itensPedido(ItemCarrinho itemCarrinho) {
        itensCarrinho.add(itemCarrinho);
    }
}
