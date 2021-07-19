package com.example.employeesservice.controller;

import com.example.employeesservice.domain.Employee;
import com.example.employeesservice.repository.EmployeeRepository;
import lombok.extern.log4j.Log4j;

import javax.servlet.http.HttpServletRequest;


/**
 * Базовый контроллер авторизации
 */
@Log4j
public abstract class AuthorizeController {

    protected Employee employee;

    public AuthorizeController(HttpServletRequest request, EmployeeRepository employeeRepository) {

        log.debug("Request header 'id' = " + request.getHeader("id"));
        log.debug("Request header 'authorize' = " + request.getHeader("Authorization"));
        var employeeId = request.getHeader("id");
        if (employeeId != null)
            this.employee = employeeRepository.findById(Long.parseLong(employeeId)).orElse(null);
    }
}
