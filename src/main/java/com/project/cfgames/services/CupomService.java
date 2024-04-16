package com.project.cfgames.services;

import com.project.cfgames.entities.*;
import com.project.cfgames.repositories.CupomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CupomService {
    @Autowired
    DataService dataService;
    @Autowired
    CupomRepository cupomRepository;

    // gera cupom a partir de um Cliente e Valor Desconto
    public void gerarCupom (Cliente cliente, Float valorDesconto) {
        Cupom cupom = criarCupom(cliente, valorDesconto);
        cupomRepository.save(cupom);
    }

    // gera cupom a partir de uma Troca
    public void gerarCupom (SolicitacaoTroca solicitacaoTroca) {
        Cupom cupom = criarCupom(solicitacaoTroca.getCliente(), calculaDesconto(solicitacaoTroca));
        cupomRepository.save(cupom);
    }

    // retorna cupom
    private Cupom criarCupom(Cliente cliente, Float valorDesconto) {
        Cupom cupom = new Cupom();

        cupom.setValorDesconto(valorDesconto);
        cupom.setData(dataService.getDateTimeNow());
        cupom.setValidade(dataService.getDateTimeValidade());
        cupom.setDisponivel(true);
        cupom.setCliente(cliente);

        return cupom;
    }

    // retorna Valor de Desconto a partir de uma Troca
    public float calculaDesconto(SolicitacaoTroca solicitacaoTroca) {
        float valorDesconto = 0f;

        for (ItemTroca itemTroca : solicitacaoTroca.getItensTroca()) {
            valorDesconto += (itemTroca.getItemCarrinho().getProduto().getPreco() * itemTroca.getQuantidadeTroca());
        }

        return valorDesconto;
    }

    // indisponibiliza o uso dos cupons para pedidos futuros
    public void cuponsUsados(Set<Cupom> cupons) {
        for (Cupom cupom : cupons) {
            Cupom cupomR = cupomRepository.getReferenceById(cupom.getCodigoCupom());

            cupomR.setDisponivel(false);

            cupomRepository.save(cupomR);
        }
    }
}
