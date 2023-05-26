package com.project.cfgames.dtos.requests;

import com.project.cfgames.entities.ItemCarrinho;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarrinhoCompraRequest {
    @Valid
    private Set<ItemCarrinho> itensCarrinho;
}
