package com.example.employeesservice.dto.request;

import lombok.Data;

@Data
public class CreateEmployeeDTO {

    public String login;
    public String password;
    public String lastName;
    public String email;
    public long roleId;
}
