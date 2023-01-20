package com.project.cfgames.services;

import com.project.cfgames.clients.FreteClient;
import com.project.cfgames.clients.responses.FreteResponse;
import com.project.cfgames.entities.Pedido;
import com.project.cfgames.entities.relations.CartaoPedido;
import com.project.cfgames.repositories.CartaoPedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Service
public class PedidoService {

    @Autowired
    FreteClient freteClient;

    // retorna valor da data e hora atual
    public LocalDateTime getDateTimeNow(){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String dateTimeNow = format.format(LocalDateTime.now());

        return LocalDateTime.parse(dateTimeNow, format);
    }

    // retorna valor total da compra (valor total carrinho + valor frete)
    public Float calcularValorTotal(Float valorCarrinho, Float valorFrete){
        return (valorCarrinho + valorFrete);
    }

    // setter de ValorParcial no cartão pedido
    public void valorParcial(Float valorTotal, Set<CartaoPedido> cartoesPedido){
        if (cartoesPedido.size() == 1) {
            for (CartaoPedido cartao : cartoesPedido){
                cartao.setValorParcial(valorTotal);
            }
        }
        else if (cartoesPedido.size() > 1) {
            int size = cartoesPedido.size();

            for (CartaoPedido cartao : cartoesPedido){
                cartao.setValorParcial(valorTotal / size);
            }
        }
    }

    // calcula o valor das parcelas de cada cartão e atribui
    public void valorParcelas(Set<CartaoPedido> cartoesPedido){
        for (CartaoPedido cartao : cartoesPedido){
            cartao.setValorParcelas(cartao.getValorParcial()/cartao.getParcelas());
        }
    }

    // setter de Id Pedido em cada cartão do pedido
    public void idPedido(Pedido pedido, Set<CartaoPedido> cartoesPedido){
        for (CartaoPedido cartao : cartoesPedido){
            cartao.setPedido(pedido);
        }
    }

    // calcula frete através da feign client api (FreteClient) e gera uma responseDTO (FreteResponse)
    public FreteResponse calcularFrete(String cepDestino, Integer peso){
        return freteClient.getFrete(cepDestino, peso);
    }


}
