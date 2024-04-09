package com.project.cfgames.entities;

import com.fasterxml.jackson.annotation.*;
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
import javax.validation.constraints.Size;
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
@JsonPropertyOrder({"id", "carrinhoCompra", "cliente", "enderecoCliente", "cartoes", "cupons", "data", "dataAtualizacao", "status", "frete", "valorTotal"})
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pedido_id")
    private Long id;

    @OneToOne
    private CarrinhoCompra carrinhoCompra;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    private Cliente cliente;

    @NotNull(message = "Campo não informado!")
    @ManyToOne
    private EnderecoCliente enderecoCliente;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime data;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime dataAtualizacao;

    @OneToMany
    @JoinTable(name = "cupons_pedidos")
    private Set<Cupom> cupons;

    private Integer prazoDias;

    private Float frete;

    private Float valorTotal;

    //enum
    private StatusPedido status;

    @Valid
    @OneToMany(mappedBy = "pedido")
    private Set<CartaoPedido> cartoes;

    public Pedido(CarrinhoCompra carrinhoCompra, Cliente cliente, EnderecoCliente enderecoCliente, Set<Cupom> cupons, Set<CartaoPedido> cartoes) {
        this.carrinhoCompra = carrinhoCompra;
        this.cliente = cliente;
        this.enderecoCliente = enderecoCliente;
        this.cupons = cupons;
        this.cartoes = cartoes;
    }
}


