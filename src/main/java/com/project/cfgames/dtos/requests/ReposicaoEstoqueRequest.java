package com.project.cfgames.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReposicaoEstoqueRequest {
    @Valid
    @NotNull(message = "Campo não informado!")
    @OneToMany(cascade = CascadeType.ALL)
    private Set<ItemReposicaoRequest> itensReposicao;

    @NotNull(message = "Campo não informado!")
    private Boolean reporEstoque;
}
