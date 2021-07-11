package com.example.autorizeservice.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ErrorResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime timestamp;
    private HttpStatus status;
    private int code;
    private String error;
    private String message;

    public ErrorResponse(int code, String error, String message, HttpStatus status) {
        this.timestamp = LocalDateTime.now();
        this.code = code;
        this.error = error;
        this.message = message;
        this.status = status;
    }
}

