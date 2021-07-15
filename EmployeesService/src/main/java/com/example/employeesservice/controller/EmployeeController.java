package com.example.employeesservice.controller;

import com.example.employeesservice.domain.enums.RoleType;
import com.example.employeesservice.dto.request.CreateEmployeeDto;
import com.example.employeesservice.dto.request.UpdateEmployeeDto;
import com.example.employeesservice.dto.response.EmployeeDto;
import com.example.employeesservice.repository.EmployeeRepository;
import com.example.employeesservice.service.EmployeeService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Log4j
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
        log.debug("Request method: employee/create. User login = " + request.getLogin());
        return employeeService.create(request);
    }

    @PostMapping("update")
    public EmployeeDto update(@RequestParam long employeeId, @RequestBody UpdateEmployeeDto request) {
        log.debug("Request method: employee/update. User login = " + request.getLogin());
        return employeeService.update(request, employeeId);
    }

    @PutMapping("appoint-role")
    public boolean appointRole(@RequestParam long employeeId, RoleType role) {
        log.debug("Request method: employee/create. UserId = " + employeeId + " role: " + role.name());
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
        log.debug("Request method: employee/block. UserId = " + id);
        this.employeeService.blockById(id);
    }

    @GetMapping("unblock")
    public void unblockById(@RequestParam long id) {
        log.debug("Request method: employee/unblock. UserId = " + id);
        this.employeeService.unblockById(id);
    }
}
