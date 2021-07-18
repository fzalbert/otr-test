package com.example.employeesservice.exception;

import org.springframework.http.HttpStatus;

/**
 * Модель отображения исключения
 */
public class FieldNotUniqueException extends BaseRuntimeException {

    public FieldNotUniqueException(String message) {
        super("Field " + message + " is not unique", HttpStatus.NOT_FOUND);
    }
}
