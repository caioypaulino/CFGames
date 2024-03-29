package com.project.cfgames.services;

import com.project.cfgames.entities.Pedido;
import com.project.cfgames.entities.SolicitacaoTroca;
import com.project.cfgames.entities.enums.StatusPedido;
import com.project.cfgames.entities.enums.StatusSolicitacao;
import com.project.cfgames.repositories.PedidoRepository;
import com.project.cfgames.repositories.SolicitacaoTrocaRepository;
import com.project.cfgames.validations.ValidationStatusPedido;
import com.project.cfgames.validations.ValidationStatusSolicitacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SolicitacaoTrocaService {
    @Autowired
    ValidationStatusSolicitacao validationStatusSolicitacao;
    @Autowired
    ValidationStatusPedido validationStatusPedido;
    @Autowired
    DataService dataService;
    @Autowired
    SolicitacaoTrocaRepository solicitacaoTrocaRepository;
    @Autowired
    PedidoRepository pedidoRepository;

    // pipeline update Status Troca
    public void updateStatusTroca(SolicitacaoTroca solicitacaoTroca, Pedido pedido, StatusSolicitacao statusSolicitacaoUpdate, StatusPedido statusPedidoUpdate) {
        validationStatusSolicitacao.updateValidate(solicitacaoTroca.getStatus(), statusSolicitacaoUpdate);
        validationStatusPedido.updateValidate(pedido.getStatus(), statusPedidoUpdate);

        solicitacaoTroca.setStatus(statusSolicitacaoUpdate);
        solicitacaoTroca.setDataAtualizacao(dataService.getDateTimeNow());

        pedido.setStatus(statusPedidoUpdate);
        pedido.setDataAtualizacao(dataService.getDateTimeNow());

        solicitacaoTrocaRepository.save(solicitacaoTroca);
        pedidoRepository.save(pedido);
    }
}
