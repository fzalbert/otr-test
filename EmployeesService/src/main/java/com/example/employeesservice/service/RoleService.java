package com.example.employeesservice.service;

import com.example.employeesservice.dto.request.CreateRoleDTO;
import com.example.employeesservice.dto.response.RoleDTO;

import java.util.List;

public interface RoleService {

    boolean create(CreateRoleDTO request);
    RoleDTO update(CreateRoleDTO request, long id);
    List<RoleDTO> getList();
    boolean delete(long id);
}
