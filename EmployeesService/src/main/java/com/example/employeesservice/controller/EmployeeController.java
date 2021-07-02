package com.example.employeesservice.controller;

import com.example.employeesservice.dto.request.CreateEmployeeDTO;
import com.example.employeesservice.dto.response.EmployeeDTO;
import com.example.employeesservice.dto.response.ShortEmployeeDTO;
import com.example.employeesservice.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("create")
    public boolean create(@RequestBody CreateEmployeeDTO request) {
        return employeeService.create(request);
    }

    @PutMapping("update")
    public EmployeeDTO update(@RequestBody CreateEmployeeDTO request, long employeeId) {
        return employeeService.update(request, employeeId);
    }

    @PutMapping("appoint-role")
    public boolean appointRole(long employeeId, long roleId) {
        return employeeService.appointRole(employeeId, roleId);
    }

    @GetMapping("{id}")
    public EmployeeDTO getById(long id) {
        return employeeService.getById(id);
    }

    @GetMapping("list")
    public List<ShortEmployeeDTO> getList() {
        return employeeService.getList();
    }

    @DeleteMapping("{id}")
    public boolean delete(long id) {
        return employeeService.delete(id);
    }
}
