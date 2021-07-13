package org.example.dto.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
public class Employee {

    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String password;
    private RoleType role;
}