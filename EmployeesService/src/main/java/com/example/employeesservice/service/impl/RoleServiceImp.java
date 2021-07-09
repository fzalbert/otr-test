package com.example.employeesservice.service.impl;

import com.example.employeesservice.domain.Role;
import com.example.employeesservice.dto.request.CreateRoleDTO;
import com.example.employeesservice.dto.response.RoleDTO;
import com.example.employeesservice.exception.ResourceNotFoundException;
import com.example.employeesservice.repository.EmployeeRepository;
import com.example.employeesservice.repository.RoleRepository;
import com.example.employeesservice.service.RoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Scope("prototype")
public class RoleServiceImp implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImp(EmployeeRepository employeeRepository, RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /** Создание роли */
    @Override
    public boolean create(CreateRoleDTO request) {
        var role = this.dtoInEntity(request, new Role());

        roleRepository.save(role);
        return true;
    }

    /** Обновление роли */
    @Override
    public RoleDTO update(CreateRoleDTO request, long id) {
        var role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        var updatedRole = this.dtoInEntity(request, role);

        roleRepository.save(updatedRole);
        return new RoleDTO(updatedRole);
    }

    /** Получить список ролей */
    @Override
    public List<RoleDTO> getList() {
        return roleRepository
                .findAll()
                .stream()
                .sorted(Comparator.comparing(Role::getTitle, Comparator.reverseOrder()))
                .map(RoleDTO::new)
                .collect(Collectors.toList());
    }

    /** Удалить роль */
    @Override
    public boolean delete(long id) {
        var role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        roleRepository.delete(role);
        return true;
    }

    /** Преобразовать из dto в сущность role */
    private Role dtoInEntity(CreateRoleDTO request, Role role) {
        BeanUtils.copyProperties(request, role);
        return role;
    }
}
