package com.example.appealsservice.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BaseRuntimeException.class)
    public ResponseEntity<ErrorResponse> generalHandler(BaseRuntimeException exception) {
        HttpStatus status = exception.getStatus();

        ErrorResponse error = new ErrorResponse(
                status.value(),
                status.getReasonPhrase(),
                exception.getMessage());

        return new ResponseEntity<>(error, status);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                               HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorResponse error = new ErrorResponse(
                status.value(),
                status.getReasonPhrase(),
                ex.getAllErrors().stream().findFirst().get().getDefaultMessage());

        return new ResponseEntity<>(error, status);
    }

}
