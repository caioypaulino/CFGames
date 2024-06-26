package com.project.cfgames.services.admin;

import com.project.cfgames.dtos.mappers.CustomMapper;
import com.project.cfgames.dtos.responses.SolicitacaoTrocaResponse;
import com.project.cfgames.entities.SolicitacaoTroca;
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
