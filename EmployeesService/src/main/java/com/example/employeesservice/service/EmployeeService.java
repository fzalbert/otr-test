package com.example.employeesservice.service;

import com.example.employeesservice.domain.enums.RoleType;
import com.example.employeesservice.dto.request.AuthDto;
import com.example.employeesservice.dto.request.CreateEmployeeDTO;
import com.example.employeesservice.dto.response.EmployeeDTO;
import com.example.employeesservice.dto.response.EmployeeModelDTO;
import com.example.employeesservice.dto.response.ShortEmployeeDTO;

import java.util.List;

public interface EmployeeService {

    EmployeeModelDTO auth(AuthDto request);
    boolean create(CreateEmployeeDTO request);
    EmployeeDTO update (CreateEmployeeDTO request, long id);
    boolean delete (long id);
    List<EmployeeDTO> getList();
    EmployeeDTO getById(long id);
    boolean appointRole(long id, RoleType role);

}
