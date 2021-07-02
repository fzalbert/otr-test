package com.example.employeesservice.service;

import com.example.employeesservice.dto.request.CreateEmployeeDTO;
import com.example.employeesservice.dto.response.EmployeeDTO;
import com.example.employeesservice.dto.response.ShortEmployeeDTO;

import java.util.List;

public interface EmployeeService {

    boolean create(CreateEmployeeDTO request);
    EmployeeDTO update (CreateEmployeeDTO request, long id);
    boolean delete (long id);
    List<ShortEmployeeDTO> getList();
    EmployeeDTO getById(long id);
    boolean appointRole(long id, long roleId);

}
