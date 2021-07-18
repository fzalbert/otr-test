package com.example.employeesservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Основной класс для создание кастомных исключений
 */
@Getter
public class BaseRuntimeException extends RuntimeException {

    private HttpStatus status;

    public BaseRuntimeException(String message){
        super(message);
    }

    public BaseRuntimeException(String message, HttpStatus status){
        super(message);
        this.status = status;
    }
}

