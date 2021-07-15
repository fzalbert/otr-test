package com.example.employeesservice.exception;

import org.springframework.http.HttpStatus;

public class TemplateException extends BaseRuntimeException {

        public TemplateException(String message) {
            super(message , HttpStatus.BAD_REQUEST);
        }

}
