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
public class EmployeeDTO implements Serializable {

    public Long id;
    public String lastName;
    public String firstName;
    public String email;
    public Integer role;
    public String login;

    public EmployeeDTO(Employee employee) {
        id = employee.getId();
        lastName = employee.getLastName();
        firstName = employee.getFirstName();
        email = employee.getEmail();
        role = employee.getRoleType().getValue();
        login = employee.getPerson().getLogin();
    }
}
