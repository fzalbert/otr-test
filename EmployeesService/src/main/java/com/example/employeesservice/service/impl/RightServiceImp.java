package com.example.employeesservice.service.impl;

import com.example.employeesservice.domain.Right;
import com.example.employeesservice.dto.response.RightDto;
import com.example.employeesservice.exception.ResourceNotFoundException;
import com.example.employeesservice.exception.TemplateException;
import com.example.employeesservice.repository.RightRepository;
import com.example.employeesservice.repository.RoleRepository;
import com.example.employeesservice.service.RightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Scope("prototype")
public class RightServiceImp implements RightService {

    private final RoleRepository roleRepository;
    private final RightRepository rightRepository;

    @Autowired
    public RightServiceImp(RoleRepository roleRepository, RightRepository rightRepository) {
        this.roleRepository = roleRepository;
        this.rightRepository = rightRepository;
    }

    /**
     * Добавить права для определенной роли
     */
    @Override
    public boolean appointRole(long id, long roleId) {
        var role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException(roleId));
        var right = rightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        role.getRights().add(right);
        roleRepository.save(role);
        return true;
    }

    /**
     * Получить список всех прав
     */
    @Override
    public List<RightDto> getList() {
        return rightRepository
                .findAll(Sort.by(Sort.Direction.ASC, "title"))
                .stream()
                .map(RightDto::new)
                .collect(Collectors.toList());
    }

    /**
     * Получить список прав, относящихся к выбранной роле
     */
    @Override
    public List<RightDto> getListByRoleId(long roleId) {
        var role = roleRepository.findById(roleId)
                .orElseThrow(() -> new TemplateException("Роль не найдена"));

        return role.getRights()
                .stream()
                .map(RightDto::new)
                .collect(Collectors.toList());
    }
}
