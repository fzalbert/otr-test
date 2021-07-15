package com.example.employeesservice.service;

import com.example.employeesservice.domain.enums.RoleType;
import com.example.employeesservice.dto.request.AuthDto;
import com.example.employeesservice.dto.request.CreateEmployeeDto;
import com.example.employeesservice.dto.request.UpdateEmployeeDto;
import com.example.employeesservice.dto.response.EmployeeDto;
import com.example.employeesservice.dto.response.EmployeeModelDto;

import java.util.List;

public interface EmployeeService {

    EmployeeModelDto auth(AuthDto request);
    boolean create(CreateEmployeeDto request);
    EmployeeDto update (UpdateEmployeeDto request, long id);
    boolean delete (long id);
    List<EmployeeDto> getList();
    EmployeeDto getById(long id);
    boolean appointRole(long id, RoleType role);
    void blockById(long id);
    void unblockById(long id);

}
