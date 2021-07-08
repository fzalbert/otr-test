package com.example.clientsservice.exception;

import org.springframework.http.HttpStatus;

public class WrongCheckNumberException extends BaseRuntimeException {

    public WrongCheckNumberException(String message) {
        super("Wrong " + message , HttpStatus.BAD_REQUEST);
    }
}
