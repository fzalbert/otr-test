package com.example.employeesservice.controller;

import com.example.employeesservice.dto.request.CreateRoleDTO;
import com.example.employeesservice.dto.response.RoleDTO;
import com.example.employeesservice.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("role")
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("create")
    public boolean create(@RequestBody CreateRoleDTO request) {
        return roleService.create(request);
    }

    @PutMapping("update")
    public RoleDTO update(@RequestBody CreateRoleDTO request, long roleId) {
        return roleService.update(request, roleId);
    }

    @GetMapping("list")
    public List<RoleDTO> list() {
        return roleService.getList();
    }

    @DeleteMapping("{id}")
    public boolean delete(long id) {
        return roleService.delete(id);
    }
}
