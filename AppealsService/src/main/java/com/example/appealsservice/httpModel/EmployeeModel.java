package com.example.appealsservice.httpModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmployeeModel {
    public Long id;
    public String name;
    public String email;
    public List<String> rights;
}
