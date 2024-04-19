package com.project.cfgames.services.admin;

import com.project.cfgames.dtos.mappers.CustomMapper;
import com.project.cfgames.dtos.responses.CupomResponse;
import com.project.cfgames.entities.Cupom;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminCupomService {
    // retorna um pedidosResponse mapeado ao receber pedidos
    public List<CupomResponse> mapPedidosResponse(List<Cupom> cupons) {
        List<CupomResponse> cuponsResponse = new ArrayList<>();

        for (Cupom cupom : cupons) {
            CupomResponse cupomResponse = new CupomResponse();

            CustomMapper.update(cupom, cupomResponse);

            cuponsResponse.add(cupomResponse);
        }

        return cuponsResponse;
    }
}
