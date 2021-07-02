package com.example.employeesservice.service;

import com.example.employeesservice.dto.request.CreateRightDTO;
import com.example.employeesservice.dto.request.CreateRoleDTO;
import com.example.employeesservice.dto.response.RightDTO;
import com.example.employeesservice.dto.response.RoleDTO;

import java.util.List;

public interface RightService {

    boolean appointRole(long id, long roleId);
    List<RightDTO> getList();
    List<RightDTO> getListByRoleId(long roleId);
}
