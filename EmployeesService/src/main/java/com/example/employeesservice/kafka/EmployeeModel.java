package com.example.employeesservice.kafka;

import com.example.employeesservice.domain.enums.RoleType;
import lombok.Data;

@Data
public class EmployeeModel {

        private Long id;
        private String name;
        private String lastName;
        private String email;
        private String password;
        private RoleType role;
}

