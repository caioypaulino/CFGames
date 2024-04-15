package com.project.cfgames.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    private Integer quantidadeTroca;

    public ItemTroca(ItemCarrinho itemCarrinho, Integer quantidadeTroca) {
        this.itemCarrinho = itemCarrinho;
        this.quantidadeTroca = quantidadeTroca;
    }
}
