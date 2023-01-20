package com.project.cfgames.strategies;

import com.project.cfgames.entities.Pedido;
import com.project.cfgames.entities.relations.CartaoPedido;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class StrategyPedido {

    // valida quantidade de cartoes
    public boolean doisCartoesValidate(Pedido pedido) {
        Set<CartaoPedido> cartoesPedido = pedido.getCartoes();

        if (cartoesPedido.size() <= 2){
            return true;
        }
        else {
            throw new RuntimeException("Quantidade de Cartões Inválido");
        }
    }

    // valida valor parcial caso tenha um cartao pedido sem valor parcial definido
    public boolean valorParcialNullValidate(Pedido pedido) {
        Set<CartaoPedido> cartoesPedido = pedido.getCartoes();

        for(CartaoPedido cartao : cartoesPedido) {
            return cartao.getValorParcial() == null;
        }

        return false;
    }
}
