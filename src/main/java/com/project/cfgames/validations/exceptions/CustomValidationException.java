package com.project.cfgames.validations.exceptions;

public class CustomValidationException extends Exception {
    public CustomValidationException(String mensagemErro) {
        super(mensagemErro);
    }
}
