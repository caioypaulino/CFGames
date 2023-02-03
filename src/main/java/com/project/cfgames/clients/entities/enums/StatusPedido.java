package com.project.cfgames.clients.entities.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum StatusPedido {
    EM_PROCESSAMENTO,
    APROVADO,
    EM_TRANSITO,
    ENTREGUE,
    EM_TROCA,
    TROCA_AUTORIZADA,
    REPROVADO;
}
