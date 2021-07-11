package com.example.employeesservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtParseResponseDTO {

    private Long userId;

    private List<String> authorities;
}
