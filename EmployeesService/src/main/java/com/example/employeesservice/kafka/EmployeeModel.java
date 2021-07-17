package com.example.employeesservice.kafka;

import com.example.employeesservice.domain.Employee;
import lombok.Data;

@Data
public class EmployeeModel {

    private Long id;
    private String login;
    private String name;
    private String lastName;
    private String email;
    private String password;

    public EmployeeModel(){

    }

    public EmployeeModel(Employee employee)
    {
        id = employee.getId();
        login = employee.getPerson().getLogin();
        name = employee.getFirstName();
        lastName = employee.getLastName();
        email = employee.getEmail();
    }
}

