package com.example.employeesservice.service.impl;

import com.example.employeesservice.domain.Role;
import com.example.employeesservice.dto.request.CreateRoleDto;
import com.example.employeesservice.dto.response.RoleDto;
import com.example.employeesservice.exception.ResourceNotFoundException;
import com.example.employeesservice.exception.TemplateException;
import com.example.employeesservice.repository.EmployeeRepository;
import com.example.employeesservice.repository.RoleRepository;
import com.example.employeesservice.service.RoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Sort;
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

    /**
     * Создание роли
     */
    @Override
    public boolean create(CreateRoleDto request) {
        var role = this.dtoInEntity(request, new Role());

        roleRepository.save(role);
        return true;
    }

    /**
     * Обновление роли
     */
    @Override
    public RoleDto update(CreateRoleDto request, long id) {
        var role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        var updatedRole = this.dtoInEntity(request, role);

        roleRepository.save(updatedRole);
        return new RoleDto(updatedRole);
    }

    /**
     * Получить список ролей
     */
    @Override
    public List<RoleDto> getList() {
        return roleRepository
                .findAll(Sort.by(Sort.Direction.ASC, "title"))
                .stream()
                .sorted()
                .map(RoleDto::new)
                .collect(Collectors.toList());
    }

    /**
     * Удалить роль
     */
    @Override
    public boolean delete(long id) {
        var role = roleRepository.findById(id)
                .orElseThrow(() -> new TemplateException("Роль не найдена"));

        roleRepository.delete(role);
        return true;
    }

    /**
     * Преобразовать из dto в сущность role
     */
    private Role dtoInEntity(CreateRoleDto request, Role role) {
        BeanUtils.copyProperties(request, role);
        return role;
    }
}
