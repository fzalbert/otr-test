package com.example.employeesservice.controller;

import com.example.employeesservice.domain.enums.RoleType;
import com.example.employeesservice.dto.request.CreateEmployeeDto;
import com.example.employeesservice.dto.request.UpdateEmployeeDto;
import com.example.employeesservice.dto.response.EmployeeDto;
import com.example.employeesservice.repository.EmployeeRepository;
import com.example.employeesservice.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@Scope("prototype")
@RequestMapping("employee")
public class EmployeeController extends AuthorizeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService, EmployeeRepository employeeRepository,
                              HttpServletRequest request) {
        super(request, employeeRepository);
        this.employeeService = employeeService;
    }

    @PostMapping("create")
    public boolean create(@Valid @RequestBody CreateEmployeeDto request) {
        return employeeService.create(request);
    }

    @PostMapping("update")
    public EmployeeDto update(@RequestParam long employeeId, @RequestBody UpdateEmployeeDto request) {
        return employeeService.update(request, employeeId);
    }

    @PutMapping("appoint-role")
    public boolean appointRole(@RequestParam long employeeId, RoleType role) {
        return employeeService.appointRole(employeeId, role);
    }

    @GetMapping("by-id")
    public EmployeeDto getById(@RequestParam long id) {
        return employeeService.getById(id);
    }

    @GetMapping("list")
    public List<EmployeeDto> getList() {
        return employeeService.getList();
    }

    @DeleteMapping("delete")
    public boolean delete(@RequestParam long id) {
        return employeeService.delete(id);
    }

    @GetMapping("block")
    public void blockById(@RequestParam long id) {

        this.employeeService.blockById(id);
    }

    @GetMapping("unblock")
    public void unblockById(@RequestParam long id) {

        this.employeeService.unblockById(id);
    }
}
