package com.example.employeesservice.controller;

import com.example.employeesservice.domain.Employee;
import com.example.employeesservice.repository.EmployeeRepository;

import javax.servlet.http.HttpServletRequest;


/**
 * Базовый контроллер авторизации
 */
public abstract class AuthorizeController {

    protected Employee employee;

    public AuthorizeController(HttpServletRequest request, EmployeeRepository employeeRepository) {
        var employeeId = request.getHeader("id");
        if (employeeId != null)
            this.employee = employeeRepository.findById(Long.parseLong(employeeId)).orElse(null);
    }
}
