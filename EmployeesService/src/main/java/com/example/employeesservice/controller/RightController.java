package com.example.employeesservice.controller;

import com.example.employeesservice.dto.request.CreateEmployeeDTO;
import com.example.employeesservice.dto.response.EmployeeDTO;
import com.example.employeesservice.dto.response.RightDTO;
import com.example.employeesservice.service.EmployeeService;
import com.example.employeesservice.service.RightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("right")
public class RightController {

    private final RightService rightService;

    @Autowired
    public RightController(RightService rightService) {
        this.rightService = rightService;
    }

    @GetMapping("list")
    public List<RightDTO> getList() {
        return rightService.getList();
    }

    @GetMapping("list-by-role-id")
    public List<RightDTO> listByRoleId(long roleId) {
        return rightService.getListByRoleId(roleId);
    }

    @PostMapping("appoint-role")
    public boolean appointRole(long id, long roleId) {
        return rightService.appointRole(id, roleId);
    }
}
