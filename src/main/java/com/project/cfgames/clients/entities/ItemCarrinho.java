package com.project.cfgames.clients.entities;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Table(name = "ITENS_CARRINHO")
@Entity(name = "ItemCarrinho")

@Getter
@Setter
@ToString
@NoArgsConstructor

@JsonIdentityInfo(
        scope = ItemCarrinho.class,
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ItemCarrinho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_carrinho_id")
    private Long id;

    @ManyToOne
    private Produto produto;

    private Integer quantidade;

    private Float valorItem;

    public ItemCarrinho(Produto produto, Integer quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

}
