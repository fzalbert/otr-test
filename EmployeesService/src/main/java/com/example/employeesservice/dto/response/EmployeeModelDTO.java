package com.example.employeesservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeModelDTO {
    private Long id;
    private String email;
    private String name;
    private String role;
}
