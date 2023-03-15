package com.project.cfgames.validations;

import com.project.cfgames.dtos.requests.ClienteRequest;
import com.project.cfgames.entities.Cliente;
import com.project.cfgames.exceptions.CustomValidationException;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
public class ValidationCliente {

    // valida idade (18+)
    @SneakyThrows
    public void idadeValidate(Cliente cliente) {
        if ((Period.between(cliente.getDataNascimento(), LocalDate.now()).getYears()) < 18) {
            throw new CustomValidationException("Data de nascimento deve conter idade maior ou igual a 18 anos.");
        }
    }

    @SneakyThrows
    private void idadeValidate(ClienteRequest request) {
        if ((Period.between(request.getDataNascimento(), LocalDate.now()).getYears()) < 18) {
            throw new CustomValidationException("Data de nascimento deve conter idade maior ou igual a 18 anos.");
        }
    }

    // valida confirmação de senha
    @SneakyThrows
    public void confirmaSenhaValidate(Cliente cliente) {
        if (!(cliente.getSenha().matches(cliente.getConfirmaSenha()))) {
            throw new CustomValidationException("Senha e Confirmação Senha devem ser iguais.");
        }
    }

    @SneakyThrows
    public void confirmaSenhaValidate(ClienteRequest request) {
        if (request.getConfirmaSenha() == null) {
            throw new CustomValidationException("Confirmação Senha null.");
        }
        else if (!(request.getSenha().matches(request.getConfirmaSenha()))) {
            throw new CustomValidationException("Senha e Confirmação Senha devem ser iguais.");
        }
    }

    public void allValidates(Cliente cliente) {
        idadeValidate(cliente);
        confirmaSenhaValidate(cliente);
    }

    public void updateAllValidates(ClienteRequest request) {
        if (request.getDataNascimento() != null) {
            idadeValidate(request);
        }
        if (request.getSenha() != null) {
            confirmaSenhaValidate(request);
        }
    }


}
