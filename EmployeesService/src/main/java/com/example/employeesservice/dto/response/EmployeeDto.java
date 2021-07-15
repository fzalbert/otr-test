package com.example.employeesservice.dto.response;

import com.example.employeesservice.domain.Employee;
import com.example.employeesservice.domain.Person;
import com.example.employeesservice.domain.Role;
import com.example.employeesservice.domain.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class EmployeeDto implements Serializable {

    private Long id;
    private String lastName;
    private String firstName;
    private String email;
    private Integer role;
    private String login;
    private boolean isActive;

    public EmployeeDto(Employee employee) {
        id = employee.getId();
        lastName = employee.getLastName();
        firstName = employee.getFirstName();
        email = employee.getEmail();
        role = employee.getRoleType().getValue();
        login = employee.getPerson().getLogin();
        isActive = employee.getPerson().isActive();
    }
}
