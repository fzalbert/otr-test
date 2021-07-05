package com.example.clientsservice.exception;

import org.springframework.http.HttpStatus;

public class MissingRequiredFieldException extends BaseRuntimeException {

    public MissingRequiredFieldException(String message) {
        super("Field '" + message + "' is required", HttpStatus.BAD_REQUEST);
    }
}
