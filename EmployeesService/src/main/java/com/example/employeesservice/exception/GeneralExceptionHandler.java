package com.example.employeesservice.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.annotation.ServletSecurity;

/**
 * @ExceptionHandler refers to the exception class that we want to handle.
 * In this project every custom exception is handled by this specific handler.
 * We achieve this, by passing the corresponding http status to the exception
 * constructor, in order to be accessible here through the exception instance.
 *
 * Every custom exception we want to handle using this handler, has to extend
 * the BaseRuntimeException class and specify an HttpStatus property.
 */
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
