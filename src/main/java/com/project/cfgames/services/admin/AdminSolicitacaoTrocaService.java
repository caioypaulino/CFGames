package com.project.cfgames.services.admin;

import com.project.cfgames.dtos.mappers.CustomMapper;
import com.project.cfgames.dtos.responses.SolicitacaoTrocaResponse;
import com.project.cfgames.entities.Pedido;
import com.project.cfgames.entities.SolicitacaoTroca;
import com.project.cfgames.entities.enums.StatusPedido;
import com.project.cfgames.entities.enums.StatusSolicitacao;
import com.project.cfgames.repositories.PedidoRepository;
import com.project.cfgames.repositories.SolicitacaoTrocaRepository;
import com.project.cfgames.services.DataService;
import com.project.cfgames.validations.ValidationStatusPedido;
import com.project.cfgames.validations.ValidationStatusSolicitacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminSolicitacaoTrocaService {
    // retorna um solicitacoesResponse mapeado ao receber solicitacoes
    public List<SolicitacaoTrocaResponse> mapSolicitacoesTrocaResponse(List<SolicitacaoTroca> solicitacoes) {
        List<SolicitacaoTrocaResponse> solicitacoesResponse = new ArrayList<>();

        for (SolicitacaoTroca solicitacao : solicitacoes) {
            SolicitacaoTrocaResponse solicitacaoResponse = new SolicitacaoTrocaResponse();

            CustomMapper.update(solicitacao, solicitacaoResponse);

            solicitacoesResponse.add(solicitacaoResponse);
        }

        return solicitacoesResponse;
    }
}
