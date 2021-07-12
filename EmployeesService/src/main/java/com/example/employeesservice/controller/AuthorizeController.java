package com.example.employeesservice.controller;

import com.example.employeesservice.domain.Employee;
import com.example.employeesservice.repository.EmployeeRepository;

import javax.servlet.http.HttpServletRequest;

public abstract class AuthorizeController {

    protected final Employee employee;

    public AuthorizeController(HttpServletRequest request, EmployeeRepository employeeRepository) {

        Long employeeId = Long.parseLong(request.getHeader("id"));
        this.employee = employeeRepository.findById(employeeId).orElse(null);
    }
}
