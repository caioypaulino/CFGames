package com.project.cfgames.exceptions;

public class CustomValidationException extends Exception {
    public CustomValidationException(String mensagemErro) {
        super(mensagemErro);
    }
}
