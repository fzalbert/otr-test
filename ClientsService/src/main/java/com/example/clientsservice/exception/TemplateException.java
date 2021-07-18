package com.example.clientsservice.exception;

import org.springframework.http.HttpStatus;

/**
 * Универсальное исключение
 */
public class TemplateException extends BaseRuntimeException {

    public TemplateException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
