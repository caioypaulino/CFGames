package com.project.cfgames.validations;

import com.project.cfgames.dtos.requests.CartaoRequest;
import com.project.cfgames.entities.Cartao;
import com.project.cfgames.entities.Cliente;
import com.project.cfgames.entities.enums.BandeiraCartao;
import com.project.cfgames.exceptions.CustomValidationException;
import com.project.cfgames.repositories.CartaoRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.Map;

@Service
public class ValidationCartao {
    @Autowired
    CartaoRepository cartaoRepository;

    // valida numero cartao
    @SneakyThrows
    public BandeiraCartao numeroCartaoValidate(Cartao cartao) {

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

        if (vencimento.isBefore(YearMonth.now())) {
            throw new CustomValidationException("Vencimento do Cartão Inválido");
        }
    }

    @SneakyThrows
    public void vencimentoValidate(CartaoRequest request) {
        YearMonth vencimento = YearMonth.of(request.getAnoVencimento(), request.getMesVencimento());

        if (!(vencimento.isAfter(YearMonth.now()))) {
            throw new CustomValidationException("Vencimento do Cartão Inválido");
        }
    }

    // valida cartao cliente
    @SneakyThrows
    public void cartaoClienteValidate(Cliente cliente, Cartao cartao) {
        if (cartaoRepository.selectCartaoCliente(cliente.getId(), cartao.getNumeroCartao()) != null) {
            throw new CustomValidationException("Cartão já adicionado");
        }
    }

    // todas as validações
    public void allValidates(Cartao cartao) {
        numeroCartaoValidate(cartao);
        vencimentoValidate(cartao);
    }
}

