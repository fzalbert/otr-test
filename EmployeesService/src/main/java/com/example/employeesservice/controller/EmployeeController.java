package com.example.employeesservice.controller;

import com.example.employeesservice.dto.request.CreateEmployeeDTO;
import com.example.employeesservice.dto.response.EmployeeDTO;
import com.example.employeesservice.dto.response.ShortEmployeeDTO;
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

    @GetMapping("auth")
    public Long auth(String login, String password) {
        return employeeService.auth(login, password);
    }

    @PostMapping("create")
    public boolean create(@Valid @RequestBody CreateEmployeeDTO request) {
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
        String name = employee.getLastName();
        return employeeService.getList();
    }

    @DeleteMapping("{id}")
    public boolean delete(long id) {
        return employeeService.delete(id);
    }
}
