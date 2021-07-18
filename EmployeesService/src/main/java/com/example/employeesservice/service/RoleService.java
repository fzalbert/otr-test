package com.example.employeesservice.service;

import com.example.employeesservice.dto.request.CreateRoleDto;
import com.example.employeesservice.dto.response.RoleDto;

import java.util.List;

public interface RoleService {

    boolean create(CreateRoleDto request);

    RoleDto update(CreateRoleDto request, long id);

    List<RoleDto> getList();

    boolean delete(long id);
}
