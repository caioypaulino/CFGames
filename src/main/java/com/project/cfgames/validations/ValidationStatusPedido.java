package com.project.cfgames.validations;

import com.project.cfgames.entities.Cliente;
import com.project.cfgames.entities.Pedido;
import com.project.cfgames.entities.enums.StatusPedido;
import com.project.cfgames.exceptions.CustomValidationException;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static com.project.cfgames.entities.enums.StatusPedido.PRODUTOS_TROCA_ENVIADOS;
import static com.project.cfgames.entities.enums.StatusPedido.PRODUTOS_TROCA_RECEBIDOS;

@Service
public class ValidationStatusPedido {
    @SneakyThrows
    public void updateValidate(StatusPedido statusAtual, StatusPedido statusUpdate) {
        switch (statusUpdate) {
            case PAGAMENTO_APROVADO:
                if (statusAtual != StatusPedido.EM_PROCESSAMENTO && statusAtual != StatusPedido.PAGAMENTO_REPROVADO) {
                    throw new CustomValidationException("Pedido não autorizado para aprovação de pagamento.");
                }
                break;
            case PAGAMENTO_REPROVADO:
                if (statusAtual != StatusPedido.EM_PROCESSAMENTO && statusAtual != StatusPedido.PAGAMENTO_APROVADO) {
                    throw new CustomValidationException("Pedido não autorizado para reprovação de pagamento.");
                }
                break;
            case EM_TRANSITO:
                if (statusAtual != StatusPedido.PAGAMENTO_APROVADO) {
                    throw new CustomValidationException("Pedido não autorizado para despache.");
                }
                break;
            case ENTREGUE:
                if (statusAtual != StatusPedido.EM_TRANSITO) {
                    throw new CustomValidationException("Pedido não autorizado para confirmação de entrega.");
                }
                break;
            case TROCA_APROVADA:
            case TROCA_REPROVADA:
                if (statusAtual != StatusPedido.TROCA_SOLICITADA) {
                    throw new CustomValidationException("Pedido não autorizado para aprovação ou reprovação de troca.");
                }
                break;
            case PRODUTOS_TROCA_ENVIADOS:
                if (statusAtual != StatusPedido.TROCA_APROVADA && statusAtual != PRODUTOS_TROCA_RECEBIDOS) {
                    throw new CustomValidationException("Pedido não autorizado para envio do(s) item(ns) de troca.");
                }
                break;
            case PRODUTOS_TROCA_RECEBIDOS:
                if (statusAtual != PRODUTOS_TROCA_ENVIADOS && statusAtual != PRODUTOS_TROCA_RECEBIDOS) {
                    throw new CustomValidationException("Pedido não autorizado para confirmação de recebimento do(s) item(ns) de troca.");
                }
                break;
            case TROCA_FINALIZADA:
                if (statusAtual != PRODUTOS_TROCA_RECEBIDOS && statusAtual != StatusPedido.TROCA_APROVADA) {
                    throw new CustomValidationException("Pedido não autorizado para finalização de troca.");
                }
                break;
            case TROCA_CANCELADA:
                if (statusAtual != StatusPedido.TROCA_APROVADA && statusAtual != StatusPedido.TROCA_SOLICITADA){
                    throw new CustomValidationException("Pedido não autorizado para cancelamento de troca.");
                }
                break;
            case CANCELADO:
                if (statusAtual != StatusPedido.EM_PROCESSAMENTO && statusAtual != StatusPedido.PAGAMENTO_APROVADO) {
                    throw new CustomValidationException("Pedido não autorizado para cancelamento, entre em contato com o suporte.");
                }
                break;
        }
    }

    // valida se pedido pertence ao cliente
    @SneakyThrows
    private void clientePedidoValidate(Cliente cliente, Pedido pedido) {
        if (!cliente.equals(pedido.getCliente())) {
            throw new CustomValidationException("Pedido id: " + pedido.getId() + " , não pertence ao cliente logado.");
        }
    }
    public void cancelamentoValidate(Cliente cliente, Pedido pedido, StatusPedido statusUpdate) {
        clientePedidoValidate(cliente, pedido);
        updateValidate(pedido.getStatus(), statusUpdate);
    }


}
