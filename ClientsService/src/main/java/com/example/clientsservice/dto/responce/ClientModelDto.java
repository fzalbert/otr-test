package com.example.clientsservice.dto.responce;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientModelDto {
    private Long id;
    private String email;
    private String name;
    private String role;
}
