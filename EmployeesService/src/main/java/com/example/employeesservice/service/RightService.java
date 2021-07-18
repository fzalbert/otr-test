package com.example.employeesservice.service;

import com.example.employeesservice.dto.response.RightDto;

import java.util.List;

public interface RightService {

    boolean appointRole(long id, long roleId);

    List<RightDto> getList();

    List<RightDto> getListByRoleId(long roleId);
}
