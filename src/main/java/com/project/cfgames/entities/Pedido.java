package com.project.cfgames.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.project.cfgames.entities.enums.StatusPedido;
import com.project.cfgames.entities.relations.CartaoPedido;
import com.project.cfgames.entities.relations.EnderecoCliente;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Table(name = "PEDIDOS")
@Entity(name = "Pedido")

@Getter
@Setter
@ToString
@NoArgsConstructor

@JsonIdentityInfo(
        scope = Pedido.class,
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pedido_id")
    private Long id;

    @NotNull(message = "Campo não informado!")
    @OneToOne
    private CarrinhoCompra carrinhoCompra;

    @NotNull(message = "Campo não informado!")
    @ManyToOne
    private Cliente cliente;

    @NotNull(message = "Campo não informado!")
    @ManyToOne
    private EnderecoCliente enderecoCliente;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime data;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime dataAtualizacao;

    private String cupom;

    private Float frete;

    private Float valorTotal;

    //enum
    private StatusPedido status;

    @Valid
    @NotNull(message = "Campo não informado!")
    @OneToMany(mappedBy = "pedido")
    private Set<CartaoPedido> cartoes;

    public Pedido(CarrinhoCompra carrinhoCompra, Cliente cliente, EnderecoCliente enderecoCliente, String cupom, Set<CartaoPedido> cartoes) {
        this.carrinhoCompra = carrinhoCompra;
        this.cliente = cliente;
        this.enderecoCliente = enderecoCliente;
        this.cupom = cupom;
        this.cartoes = cartoes;
    }
}


