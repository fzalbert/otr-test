package com.example.employeesservice.controller;

import com.example.employeesservice.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Scope("prototype")
@RequestMapping("account")
public class AccountController {

    private final EmployeeService employeeService;

    @Autowired
    public AccountController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("auth")
    public Long auth(String login, String password) {
        return employeeService.auth(login, password);
    }
}
