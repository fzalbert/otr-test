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
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService, HttpServletRequest request) {
        this.employeeService = employeeService;
    }

    /**
     * Создать сотрудника
     * @param request
     */
    @PostMapping("create")
    public boolean create(@Valid @RequestBody CreateEmployeeDto request) {
        log.debug("Request method: employee/create. User login = " + request.getLogin());
        return employeeService.create(request);
    }

    /**
     * Обновить сотрудника
     * @param employeeId
     * @param request
     */
    @PostMapping("update")
    public EmployeeDto update(@RequestParam long employeeId, @RequestBody UpdateEmployeeDto request) {
        log.debug("Request method: employee/update. User login = " + request.getEmail());
        return employeeService.update(request, employeeId);
    }

    /**
     * Назначить роль
     * @param employeeId
     * @param role
     */
    @PutMapping("appoint-role")
    public boolean appointRole(@RequestParam long employeeId, RoleType role) {
        log.debug("Request method: employee/create. UserId = " + employeeId + " role: " + role.name());
        return employeeService.appointRole(employeeId, role);
    }

    /**
     * Получение сотрудника по id
     * @param id
     */
    @GetMapping("by-id")
    public EmployeeDto getById(@RequestParam long id) {
        return employeeService.getById(id);
    }

    /**
     * Получение списка сотрудников
     */
    @GetMapping("list")
    public List<EmployeeDto> getList() {
        return employeeService.getList();
    }

    /**
     * Удалить сотрудника по id
     * @param id
     */
    @DeleteMapping("delete")
    public boolean delete(@RequestParam long id) {
        return employeeService.delete(id);
    }

    /**
     * Заблокировать пользователя
     * @param id
     */
    @GetMapping("block")
    public void blockById(@RequestParam long id) {
        log.debug("Request method: employee/block. UserId = " + id);
        this.employeeService.blockById(id);
    }

    /**
     * Разблокировать пользователя
     * @param id
     */
    @GetMapping("unblock")
    public void unblockById(@RequestParam long id) {
        log.debug("Request method: employee/unblock. UserId = " + id);
        this.employeeService.unblockById(id);
    }
}
