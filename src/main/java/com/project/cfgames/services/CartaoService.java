package com.project.cfgames.facades.services;

import com.project.cfgames.entities.Cartao;
import com.project.cfgames.entities.enums.BandeiraCartao;
import com.project.cfgames.exceptions.CustomValidationException;
import com.project.cfgames.validations.ValidationCartao;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;

@Service
public class CartaoService {

    @Autowired
    ValidationCartao validationCartao;

    public BandeiraCartao bandeiraCartao(Cartao cartao) {
        return validationCartao.numeroCartaoValidade(cartao);
    }

}
