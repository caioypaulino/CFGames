package com.project.cfgames.validations;

import com.project.cfgames.dtos.requests.CupomUpdateRequest;
import com.project.cfgames.dtos.requests.ProdutoRequest;
import com.project.cfgames.entities.Cupom;
import com.project.cfgames.entities.Produto;
import com.project.cfgames.exceptions.CustomValidationException;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
public class ValidationCupom {

    // valida validade
    @SneakyThrows
    public void updateValidadeValidate(CupomUpdateRequest request, Cupom cupom) {
        if (request.getValidade().isBefore(cupom.getData())){
            throw new CustomValidationException("Validade inválida.<br> Data de validade deve ser posterior a data de lançamento do cupom.");
        }
    }

    // valida indisponível
    @SneakyThrows
    public void cupomIndisponivelValidate(Cupom cupom) {
        if (cupom.getDisponivel().equals(false)) {
            throw new CustomValidationException("Não é possível alterar cupom.<br> Cupom indisponível");
        }
    }

    // valida cupom UPDATE
    @SneakyThrows
    public void updateValidate(CupomUpdateRequest request, Cupom cupom) {
        cupomIndisponivelValidate(cupom);

        if (request.getValidade() != null) {
            updateValidadeValidate(request, cupom);
        }
    }
}
