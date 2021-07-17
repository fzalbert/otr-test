package org.example.dto.user;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Data
public class Employee {

    private Long id;
    private String login;
    private String name;
    private String lastName;
    private String email;
    private String password;
    private RoleType role;
}