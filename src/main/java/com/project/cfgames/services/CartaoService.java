package com.project.cfgames.services;

import com.project.cfgames.entities.Cartao;
import com.project.cfgames.entities.enums.BandeiraCartao;
import com.project.cfgames.validations.ValidationCartao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartaoService {

    @Autowired
    ValidationCartao validationCartao;

    public BandeiraCartao bandeiraCartao(Cartao cartao) {
        return validationCartao.numeroCartaoValidate(cartao);
    }

}
