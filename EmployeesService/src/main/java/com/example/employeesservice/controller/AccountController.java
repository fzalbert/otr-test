package com.example.employeesservice.controller;

import com.example.employeesservice.dto.request.AuthDto;
import com.example.employeesservice.dto.response.EmployeeModelDTO;
import com.example.employeesservice.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Scope("prototype")
@RequestMapping("account")
public class AccountController {

    private final EmployeeService employeeService;

    @Autowired
    public AccountController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("auth")
    public EmployeeModelDTO auth(@RequestBody @Valid AuthDto request) {
        return employeeService.auth(request);
    }
}
