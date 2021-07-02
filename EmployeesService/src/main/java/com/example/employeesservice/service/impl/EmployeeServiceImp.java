package com.example.employeesservice.service.impl;

import com.example.employeesservice.domain.Employee;
import com.example.employeesservice.domain.Person;
import com.example.employeesservice.dto.request.CreateEmployeeDTO;
import com.example.employeesservice.dto.response.EmployeeDTO;
import com.example.employeesservice.dto.response.ShortEmployeeDTO;
import com.example.employeesservice.exception.ResourceNotFoundException;
import com.example.employeesservice.repository.EmployeeRepository;
import com.example.employeesservice.repository.RoleRepository;
import com.example.employeesservice.service.EmployeeService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImp implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;

    public EmployeeServiceImp(EmployeeRepository employeeRepository, RoleRepository roleRepository) {
        this.employeeRepository = employeeRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public boolean create(CreateEmployeeDTO request) {
        var employee = this.dtoToEntity(request, new Employee());
        employeeRepository.save(employee);
        return true;
    }

    @Override
    public EmployeeDTO update(CreateEmployeeDTO request, long employeeId) {
        var employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException(employeeId));
        var updatedEmployee = this.dtoToEntity(request, employee);
        employeeRepository.save(updatedEmployee);

        return new EmployeeDTO(updatedEmployee);
    }

    @Override
    public boolean delete(long employeeId) {
        var employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException(employeeId));
        employeeRepository.delete(employee);
        return true;
    }

    @Override
    public List<ShortEmployeeDTO> getList() {

        return employeeRepository
                .findAll()
                .stream()
                .sorted(Comparator.comparing(Employee::getLastName, Comparator.reverseOrder()))
                .map(ShortEmployeeDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDTO getById(long employeeId) {
        var employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException(employeeId));
        return new EmployeeDTO(employee);
    }

    @Override
    public boolean appointRole(long employeeId, long roleId) {
        var employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException(employeeId));
        var role = roleRepository.getById(roleId);

        employee.setRole(role);
        employeeRepository.save(employee);
        return true;
    }

    private Employee dtoToEntity(CreateEmployeeDTO request, Employee employee) {
        BeanUtils.copyProperties(request, employee);

        var person = new Person();
        BeanUtils.copyProperties(request, person, "password");
        person.setPassword(DigestUtils.md5Hex(request.password));
        person.setRegistrationDate(new Date());
        employee.setPerson(person);

        var role = roleRepository.findById(request.roleId)
                .orElseThrow(() -> new ResourceNotFoundException(request.roleId));
        employee.setRole(role);

        return employee;
    }
}
