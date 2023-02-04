package com.project.cfgames.entities.enums;

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
