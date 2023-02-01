package com.project.cfgames.services;

import com.project.cfgames.entities.Cartao;
import com.project.cfgames.entities.enums.BandeiraCartao;
import com.project.cfgames.strategies.StrategyCartao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CartaoService {

    @Autowired
    StrategyCartao strategyCartao;

    public BandeiraCartao bandeiraCartao(Cartao cartao) {
        return strategyCartao.numeroCartaoValidade(cartao);
    }

}
