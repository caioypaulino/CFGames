package com.project.cfgames.handlers;

import com.project.cfgames.exceptions.CustomValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class HandlerValidationsExceptions {

    // handler @validation exception
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> beanHandler(MethodArgumentNotValidException exception) {
        Map<String, String> erros = new HashMap<>();

        exception.getBindingResult().getAllErrors().forEach((erro) -> {
            String campoNome = ((FieldError) erro).getField();
            String mensagemErro = erro.getDefaultMessage();

            erros.put(campoNome, mensagemErro);
        });

        return ResponseEntity.badRequest().body(erros);
    }

    // handler @validation exception Item Carrinho
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> itemCarrinhoHandler(ConstraintViolationException exception) {
        Map<String, String> erros = new HashMap<>();

        exception.getConstraintViolations().forEach((erro) -> {
            String campoNome = String.valueOf(erro.getPropertyPath());
            String mensagemErro = erro.getMessageTemplate();

            erros.put(campoNome, mensagemErro);
        });

        return ResponseEntity.badRequest().body(erros);
    }

    // handler custom validation exception
    @ExceptionHandler(CustomValidationException.class)
    public ResponseEntity<String> customHandler(CustomValidationException exception){
        return ResponseEntity.badRequest().body(exception.getMessage());
    }
}
