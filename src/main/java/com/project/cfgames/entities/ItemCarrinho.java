package com.project.cfgames.entities;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Table(name = "ITENS_CARRINHOS")
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

    @NotNull(message = "Campo não informado!")
    @ManyToOne
    private Produto produto;

    @NotNull(message = "Campo não informado!")
    @Min(value = 1, message = "Quantidade inválida.")
    private Integer quantidade;

    private Float valorItem;

    public ItemCarrinho(Produto produto, Integer quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }
}
