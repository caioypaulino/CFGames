package com.project.cfgames.validations;

import com.project.cfgames.entities.Cliente;
import com.project.cfgames.entities.SolicitacaoTroca;
import com.project.cfgames.entities.enums.StatusSolicitacao;
import com.project.cfgames.exceptions.CustomValidationException;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
public class ValidationStatusSolicitacao {
    @SneakyThrows
    public void updateValidate(StatusSolicitacao statusAtual, StatusSolicitacao statusUpdate) {
        switch (statusUpdate) {
            case APROVADA:
            case REPROVADA:
                if (statusAtual != StatusSolicitacao.PENDENTE) {
                    throw new CustomValidationException("Solicitação de Troca não autorizada para aprovação ou reprovação.");
                }
                break;
            case PRODUTOS_RECEBIDOS:
                if (statusAtual != StatusSolicitacao.APROVADA) {
                    throw new CustomValidationException("Solicitação de Troca não autorizada para confirmar recebimento de itens.");
                }
                break;
            case CONCLUIDA:
                if (statusAtual != StatusSolicitacao.PRODUTOS_RECEBIDOS && statusAtual != StatusSolicitacao.APROVADA) {
                    throw new CustomValidationException("Solicitação de Troca não autorizada para conclusão.");
                }
                break;
            case CANCELADA:
                if (statusAtual != StatusSolicitacao.PENDENTE && statusAtual != StatusSolicitacao.APROVADA) {
                    throw new CustomValidationException("Solicitação de Troca não autorizada para cancelamento.");
                }
                break;
        }
    }

    // valida se solicitação de troca pertence ao cliente
    @SneakyThrows
    public void clienteSolicitacaoValidate(Cliente cliente, SolicitacaoTroca solicitacaoTroca) {
        if (!cliente.equals(solicitacaoTroca.getCliente())) {
            throw new CustomValidationException("Solicitação de Troca id: " + solicitacaoTroca.getId() + " , não pertence ao cliente logado.");
        }
    }
}
