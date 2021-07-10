package com.example.appealsservice.exception;

import org.springframework.http.HttpStatus;

public class AuthorizeException extends BaseRuntimeException{

    public AuthorizeException() {
        super("You are not authorized", HttpStatus.UNAUTHORIZED);
    }
}
