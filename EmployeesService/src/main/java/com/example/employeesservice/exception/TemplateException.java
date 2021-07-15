package com.example.employeesservice.exception;

import org.springframework.http.HttpStatus;

    public class TemplateException extends BaseRuntimeException {

        public TemplateException(String message) {
            super(message , HttpStatus.BAD_REQUEST);
        }
        public TemplateException() {
            super("Сотрудник не найден" , HttpStatus.BAD_REQUEST);
        }

    }
