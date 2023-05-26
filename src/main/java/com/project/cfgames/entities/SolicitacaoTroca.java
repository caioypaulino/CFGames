package com.project.cfgames.entities;

import com.fasterxml.jackson.annotation.*;
import com.project.cfgames.entities.enums.StatusSolicitacao;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Table(name = "SOLICITACOES_TROCA")
@Entity(name = "SolicitacoesTroca")

@Getter
@Setter
@NoArgsConstructor

@JsonIdentityInfo(
        scope = Cliente.class,
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SolicitacaoTroca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "solicitacao_troca_id")
    private Long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    private Cliente cliente;

    @NotNull(message = "Campo não informado!")
    @OneToOne
    private Pedido pedido;

    @Valid
    @NotNull(message = "Campo não informado!")
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "solicitacao_troca_id", referencedColumnName = "solicitacao_troca_id")
    private Set<ItemTroca> itensTroca;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime data;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime dataAtualizacao;

    @NotNull(message = "Campo não informado!")
    private String observacao;

    private StatusSolicitacao status;

    public SolicitacaoTroca(Pedido pedido, Set<ItemTroca> itensTroca, String observacao) {
        this.pedido = pedido;
        this.itensTroca = itensTroca;
        this.observacao = observacao;
    }
}
