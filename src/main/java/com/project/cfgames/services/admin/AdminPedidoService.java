package com.project.cfgames.services.admin;

import com.project.cfgames.dtos.mappers.CustomMapper;
import com.project.cfgames.dtos.responses.PedidoResponse;
import com.project.cfgames.entities.Pedido;
import com.project.cfgames.entities.enums.StatusPedido;
import com.project.cfgames.repositories.PedidoRepository;
import com.project.cfgames.services.DataService;
import com.project.cfgames.validations.ValidationStatusPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminPedidoService {
    // retorna um pedidosResponse mapeado ao receber pedidos
    public List<PedidoResponse> mapPedidosResponse(List<Pedido> pedidos) {
        List<PedidoResponse> pedidosResponse = new ArrayList<>();

        for (Pedido pedido : pedidos) {
            PedidoResponse pedidoResponse = new PedidoResponse();

            CustomMapper.update(pedido, pedidoResponse);

            pedidosResponse.add(pedidoResponse);
        }

        return pedidosResponse;
    }
}
