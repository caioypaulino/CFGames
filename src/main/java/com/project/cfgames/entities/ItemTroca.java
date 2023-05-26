package com.project.cfgames.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Table(name = "ITENS_TROCAS")
@Entity(name = "ItemTroca")

@Getter
@Setter
@ToString
@NoArgsConstructor

@JsonIdentityInfo(
        scope = ItemTroca.class,
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ItemTroca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_troca_id")
    private Long id;

    @NotNull(message = "Campo não informado!")
    @ManyToOne
    private ItemCarrinho itemCarrinho;

    @NotNull(message = "Campo não informado!")
    @Min(value = 1, message = "Quantidade inválida.")
    private Integer quantidade;

    public ItemTroca(ItemCarrinho itemCarrinho, Integer quantidade) {
        this.itemCarrinho = itemCarrinho;
        this.quantidade = quantidade;
    }
}
