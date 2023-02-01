package com.project.cfgames.strategies;

import com.project.cfgames.entities.Cartao;
import com.project.cfgames.entities.enums.BandeiraCartao;
import com.project.cfgames.exceptions.CustomValidationException;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.Map;

@Service
public class StrategyCartao {
    // valida numero cartao
    @SneakyThrows
    public BandeiraCartao numeroCartaoValidade(Cartao cartao) {

        Map<BandeiraCartao, String> validacoes = Map.of(
                BandeiraCartao.AMEX, "^3[47][0-9]{13}$",
                BandeiraCartao.ELO,"^((((636368)|(438935)|(504175)|(451416)|(636297))\\d{0,10})|((5067)|(4576)|(4011))\\d{0,12})$",
                BandeiraCartao.MASTERCARD, "^(5[1-5]\\d{4}|677189)\\d{10}$",
                BandeiraCartao.VISA,"^4[0-9]{15}$"
        );

        for (Map.Entry<BandeiraCartao, String> validacao : validacoes.entrySet()) {
            if (cartao.getNumeroCartao().matches(validacao.getValue())) {
                return validacao.getKey();
            }
        }

        throw new CustomValidationException("Número de cartão inválido!");
    }

    // valida vencimento cartao
    @SneakyThrows
    public void vencimentoValidate(Cartao cartao) {
        YearMonth vencimento = YearMonth.of(cartao.getAnoVencimento(), cartao.getMesVencimento());

        if (!(vencimento.isAfter(YearMonth.now()))) {
            throw new CustomValidationException("Vencimento do Cartão Inválido");
        }
    }

    // todas as validações
    public void allValidates(Cartao cartao) {
        numeroCartaoValidade(cartao);
        vencimentoValidate(cartao);
    }
}

