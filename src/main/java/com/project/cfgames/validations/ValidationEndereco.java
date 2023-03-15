package com.project.cfgames.validations;

import com.project.cfgames.exceptions.CustomValidationException;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
public class ValidationEndereco {
    @SneakyThrows
    public void cepValidate(String cep) {
        if (!cep.matches("^[0-9]{5}-[0-9]{3}$")) {
            throw new CustomValidationException("Formato CEP inválido. Ex:(XXXXX-XXX)");
        }
    }
}
