package com.project.cfgames.validations.handlers;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

public class HandlerValidationsExceptions {

    // handler @validation exception
    public static Map<String, String> handler(MethodArgumentNotValidException exception) {
        Map<String, String> erros = new HashMap<>();

        exception.getBindingResult().getAllErrors().forEach((erro) -> {
            String campoNome = ((FieldError) erro).getField();
            String mensagemErro = erro.getDefaultMessage();

            erros.put(campoNome, mensagemErro);
        });

        return erros;
    }

    public static Map<String, String> handler(ConstraintViolationException exception) {
        Map<String, String> erros = new HashMap<>();

        exception.getConstraintViolations().forEach((erro) -> {
            String campoNome = String.valueOf(erro.getPropertyPath());
            String mensagemErro = erro.getMessageTemplate();

            erros.put(campoNome, mensagemErro);
        });

        return erros;
    }
}
