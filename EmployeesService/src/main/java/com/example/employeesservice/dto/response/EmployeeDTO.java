package com.example.employeesservice.dto.response;

import com.example.employeesservice.domain.Employee;
import com.example.employeesservice.domain.Person;
import com.example.employeesservice.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;

@Data
@AllArgsConstructor
public class EmployeeDTO {

    public Long id;
    public String lastName;
    public String email;
    public String role;

    public EmployeeDTO(Employee employee) {
        BeanUtils.copyProperties(employee, this);
        role = employee.getRole().getTitle();
    }
}
