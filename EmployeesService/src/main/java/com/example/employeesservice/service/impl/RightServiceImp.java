package com.example.employeesservice.service.impl;

import com.example.employeesservice.domain.Right;
import com.example.employeesservice.dto.response.RightDTO;
import com.example.employeesservice.exception.ResourceNotFoundException;
import com.example.employeesservice.repository.RightRepository;
import com.example.employeesservice.repository.RoleRepository;
import com.example.employeesservice.service.RightService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RightServiceImp implements RightService {

    private final RoleRepository roleRepository;
    private final RightRepository rightRepository;

    public RightServiceImp(RoleRepository roleRepository, RightRepository rightRepository) {
        this.roleRepository = roleRepository;
        this.rightRepository = rightRepository;
    }

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

    @Override
    public List<RightDTO> getList() {

        return rightRepository
                .findAll()
                .stream()
                .sorted(Comparator.comparing(Right::getTitle, Comparator.reverseOrder()))
                .map(RightDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<RightDTO> getListByRoleId(long roleId) {

        return  roleRepository
                .findAll()
                .stream()
                .filter(x -> x.getId() == roleId)
                .findFirst()
                .map(x -> x.getRights()
                        .stream()
                        .map(RightDTO::new))
                .get()
                .collect(Collectors.toList());
    }
}