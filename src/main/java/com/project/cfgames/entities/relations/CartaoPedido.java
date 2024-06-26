package com.project.cfgames.entities.relations;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.project.cfgames.entities.Cartao;
import com.project.cfgames.entities.Pedido;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Table(name = "CARTOES_PEDIDOS")
@Entity(name = "CartaoPedido")

@Getter
@Setter
@NoArgsConstructor

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CartaoPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name =  "cartao_pedido_id")
    private Long id;

    @Min(value = 10, message = "Valor parcial mínimo R$ 10,00")
    private Float valorParcial;

    @NotNull(message = "Campo não informado!")
    @Min(value = 1, message = "Número de parcelas inválido.(Min = 1)")
    @Max(value = 12, message = "Número de parcelas inválido.(Max = 12)")
    private Integer parcelas;

    private Float valorParcelas;

    @NotNull(message = "Campo não informado!")
    @ManyToOne(fetch = FetchType.LAZY)
    private Cartao cartao;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Pedido pedido;

    public CartaoPedido( Integer parcelas, Cartao cartao) {
        valorParcelas = pedido.getValorTotal();
        this.parcelas = parcelas;
        this.cartao = cartao;
    }

    public CartaoPedido(Float valorParcial, Integer parcelas, Cartao cartao) {
        this.valorParcial = valorParcial;
        this.parcelas = parcelas;
        this.cartao = cartao;
    }
}
