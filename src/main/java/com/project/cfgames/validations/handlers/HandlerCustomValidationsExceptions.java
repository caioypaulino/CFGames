package com.project.cfgames.validations.handlers;

import com.project.cfgames.exceptions.CustomValidationException;

public class HandlerCustomValidationsExceptions {

    // handler custom validation
    public static String handler(CustomValidationException exception){
        return exception.getMessage();
    }
}
