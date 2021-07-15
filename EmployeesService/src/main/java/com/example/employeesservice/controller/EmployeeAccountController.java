package com.example.employeesservice.controller;

import com.example.employeesservice.dto.request.AuthDto;
import com.example.employeesservice.dto.response.EmployeeModelDto;
import com.example.employeesservice.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Scope("prototype")
@RequestMapping("employee-account")
public class EmployeeAccountController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeAccountController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("auth")
    public EmployeeModelDto auth(@RequestBody @Valid AuthDto request) {
        return employeeService.auth(request);
    }
}
