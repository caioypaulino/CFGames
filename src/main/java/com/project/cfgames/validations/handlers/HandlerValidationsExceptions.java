package com.project.cfgames.validations.handlers;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

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
}
