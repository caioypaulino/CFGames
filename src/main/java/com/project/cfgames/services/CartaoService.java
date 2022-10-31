package com.project.cfgames.services;

import com.project.cfgames.entities.Cartao;
import com.project.cfgames.entities.enums.BandeiraCartao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CartaoService {

    public static BandeiraCartao retornaBandeira(String numeroCartao) {

        List<Map<BandeiraCartao, String>> VALIDACAO = List.of(
                Map.of(
                        BandeiraCartao.AMEX, "^3[47][0-9]{13}$",
                        BandeiraCartao.ELO,"/^4011(78|79)|^43(1274|8935)|^45(1416|7393|763(1|2))|^50(4175|6699|67[0-6][0-9]|677[0-8]|9[0-8][0-9]{2}|99[0-8][0-9]|999[0-9])|^627780|^63(6297|6368|6369)|^65(0(0(3([1-3]|[5-9])|4([0-9])|5[0-1])|4(0[5-9]|[1-3][0-9]|8[5-9]|9[0-9])|5([0-2][0-9]|3[0-8]|4[1-9]|[5-8][0-9]|9[0-8])|7(0[0-9]|1[0-8]|2[0-7])|9(0[1-9]|[1-6][0-9]|7[0-8]))|16(5[2-9]|[6-7][0-9])|50(0[0-9]|1[0-9]|2[1-9]|[3-4][0-9]|5[0-8]))/",
                        BandeiraCartao.MASTERCARD, "^((5(([1-2]|[4-5])[0-9]{8}|0((1|6)([0-9]{7}))|3(0(4((0|[2-9])[0-9]{5})|([0-3]|[5-9])[0-9]{6})|[1-9][0-9]{7})))|((508116)\\\\d{4,10})|((502121)\\\\d{4,10})|((589916)\\\\d{4,10})|(2[0-9]{15})|(67[0-9]{14})|(506387)\\\\d{4,10})",
                        BandeiraCartao.VISA,"^4[0-9]{15}$"
                )
        );

        for (Map<BandeiraCartao, String> map : VALIDACAO) {
            for (Map.Entry<BandeiraCartao, String> regex : map.entrySet()) {
                if (numeroCartao.matches(regex.getValue())) {
                    return regex.getKey();
                }
            }
        }

        throw new IllegalArgumentException("Número de cartão inválido!");
    }
}
