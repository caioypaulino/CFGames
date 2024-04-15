package com.project.cfgames.dtos.requests;

import com.project.cfgames.entities.enums.StatusSolicitacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusSolicitacaoRequest {
    private StatusSolicitacao statusSolicitacao;
}
