package com.example.appealsservice.httpModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserModel {
    private Long id;
    private String email;
    private String name;
    private String userType;
}


