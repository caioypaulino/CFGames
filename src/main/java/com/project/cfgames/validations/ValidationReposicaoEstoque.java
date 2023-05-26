package com.project.cfgames.validations;

import com.project.cfgames.dtos.requests.ItemReposicaoRequest;
import com.project.cfgames.dtos.requests.ReposicaoEstoqueRequest;
import com.project.cfgames.entities.ItemTroca;
import com.project.cfgames.entities.SolicitacaoTroca;
import com.project.cfgames.exceptions.CustomValidationException;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ValidationReposicaoEstoque {
    // valida id item reposição e quantidade
    @SneakyThrows
    public void itensReposicaoValidate (SolicitacaoTroca solicitacao, ReposicaoEstoqueRequest reposicao) {
        for (ItemReposicaoRequest itemReposicao : reposicao.getItensReposicao()) {
            boolean itemEncontrado = false;

            for (ItemTroca itemTroca : solicitacao.getItensTroca()) {
                if (Objects.equals(itemTroca.getId(), itemReposicao.getItemTroca().getId())) {
                    itemEncontrado = true;

                    if (itemTroca.getQuantidade() < itemReposicao.getQuantidade()) {
                        throw new CustomValidationException("Não foi possível realizar o recebimento de itens. (Quantidade inválida, ItemTroca id: " + itemTroca.getId() + " )");
                    }

                    break;
                }
            }

            if (!itemEncontrado) {
                throw new CustomValidationException("Não foi possível realizar o recebimento de itens. (Item Troca inválido id: " + itemReposicao.getItemTroca().getId() + ")");
            }
        }
    }
}
