package com.example.clientsservice.common.validation;

import java.io.FileNotFoundException;

public interface BaseValidator<T> {

    void validate(T obj) throws FileNotFoundException;
}
