package com.example.clientsservice.exception;

import org.springframework.http.HttpStatus;

/**
 * Поиск по id
 */
public class ResourceNotFoundException extends BaseRuntimeException {

    public ResourceNotFoundException(Long id) {
        super("Resource with " + id + " not found", HttpStatus.BAD_REQUEST);
    }
}
