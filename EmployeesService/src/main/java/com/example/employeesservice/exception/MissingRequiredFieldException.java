package com.example.employeesservice.exception;

import org.springframework.http.HttpStatus;

public class MissingRequiredFieldException extends BaseRuntimeException {

    public MissingRequiredFieldException() {
        super("Object not found", HttpStatus.BAD_REQUEST);
    }
}
