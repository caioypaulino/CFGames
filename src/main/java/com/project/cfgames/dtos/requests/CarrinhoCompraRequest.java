package com.project.cfgames.dtos.requests;

import com.project.cfgames.entities.ItemCarrinho;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarrinhoCompraRequest {
    private Set<ItemCarrinho> itensCarrinho;
}
