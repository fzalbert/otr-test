package com.example.appealsservice.exception;

import org.springframework.http.HttpStatus;

public class NotRightsException extends BaseRuntimeException{

    public NotRightsException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}
