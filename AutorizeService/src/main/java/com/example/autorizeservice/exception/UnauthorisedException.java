package com.example.autorizeservice.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class UnauthorisedException extends RuntimeException {
    private ErrorResponse errorResponse;

    public UnauthorisedException(String message, String developerMessage) {
        super(message);
        errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), message,
                developerMessage, HttpStatus.UNAUTHORIZED);
    }
}
