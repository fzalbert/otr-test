package com.example.employeesservice.common.validation;

import java.io.FileNotFoundException;

/**
 * @param <T>
 *     Class of the object that we want to validate.
 */
public interface BaseValidator<T> {

    void validate(T obj) throws FileNotFoundException;
}
