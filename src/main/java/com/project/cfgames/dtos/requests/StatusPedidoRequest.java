package com.project.cfgames.dtos.requests;

import com.project.cfgames.entities.enums.StatusPedido;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusPedidoRequest {
    private StatusPedido statusPedido;
}
