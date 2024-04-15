package com.project.cfgames.validations;

import com.project.cfgames.entities.ItemCarrinho;
import com.project.cfgames.entities.ItemTroca;
import com.project.cfgames.entities.Pedido;
import com.project.cfgames.entities.SolicitacaoTroca;
import com.project.cfgames.entities.enums.StatusPedido;
import com.project.cfgames.exceptions.CustomValidationException;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ValidationSolicitacaoTroca {

    // valida pedido
    @SneakyThrows
    public void pedidoValidate(SolicitacaoTroca solicitacao, Pedido pedido) {
        if (!Objects.equals(pedido.getCliente(), solicitacao.getCliente())){
            throw new CustomValidationException("Não foi possível realizar a solicitação de troca. (Pedido não atrelado a este cliente)");
        }
        else if (pedido.getStatus() != StatusPedido.ENTREGUE) {
            throw new CustomValidationException("Não foi possível realizar a solicitação de troca. (Pedido não entregue)");
        }
    }

    // valida item solicitação troca
    @SneakyThrows
    public void itensValidate(SolicitacaoTroca solicitacao, Pedido pedido) {
        for (ItemTroca item : solicitacao.getItensTroca()) {
            boolean itemEncontrado = false;

            for (ItemCarrinho itemCarrinho : pedido.getCarrinhoCompra().getItensCarrinho()) {
                if (Objects.equals(item.getItemCarrinho().getId(), itemCarrinho.getId())) {
                    itemEncontrado = true;

                    if (itemCarrinho.getQuantidade() < item.getQuantidadeTroca()) {
                        throw new CustomValidationException("Não foi possível realizar a solicitação de troca. (Quantidade inválida, ItemCarrinho id: " + itemCarrinho.getId() + " )");
                    }

                    break;
                }
            }

            if (!itemEncontrado) {
                throw new CustomValidationException("Não foi possível realizar a solicitação de troca. (Item inválido id: " + item.getItemCarrinho().getId() + ")");
            }
        }
    }

    public void allValidates(SolicitacaoTroca solicitacao, Pedido pedido) {
        pedidoValidate(solicitacao, pedido);
        itensValidate(solicitacao, pedido);
    }
}
