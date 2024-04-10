package com.project.cfgames.entities;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "CUPONS")
@Entity(name = "Cupom")

@Getter
@Setter
@NoArgsConstructor

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Cupom {
    @Id
    @GeneratedValue(generator = "cupomGenerator")
    @GenericGenerator(name = "cupomGenerator", strategy = "com.project.cfgames.entities.generators.CupomGenerator")
    @Column(name = "codigo_cupom")
    private String codigoCupom;

    private Float valorDesconto;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime data;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime validade;

    private Boolean disponivel;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    private Cliente cliente;

    public Cupom(Cliente cliente) { this.cliente = cliente; }
}
