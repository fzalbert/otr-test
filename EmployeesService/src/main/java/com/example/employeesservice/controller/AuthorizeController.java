package com.example.employeesservice.controller;

import com.example.employeesservice.domain.Employee;
import com.example.employeesservice.dto.response.JwtParseRequestDTO;
import com.example.employeesservice.dto.response.JwtParseResponseDTO;
import com.example.employeesservice.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public abstract class AuthorizeController {

    private final String HEADER = "Authorization";

    private final String HEADER_VALUE_PREFIX = "Bearer";

    @Value("${security.jwt.parse.url}")
    private String JWT_PARSE_URL;

    protected final Employee employee;

    public AuthorizeController(HttpServletRequest request, EmployeeRepository employeeRepository) {
        String token = request.getHeader(HEADER);
        token = token.replace(HEADER_VALUE_PREFIX + " ", "");
        JwtParseResponseDTO response = parseJwt(token);

        this.employee = employeeRepository.findById(response.getUserId()).orElse(null);
    }

    private JwtParseResponseDTO parseJwt(String token) {
        RestTemplate restTemplate = new RestTemplate();

        JwtParseResponseDTO responseDto = restTemplate.postForObject(JWT_PARSE_URL, new JwtParseRequestDTO(token),
                JwtParseResponseDTO.class);

        Objects.requireNonNull(responseDto);
        return responseDto;
    }
}
