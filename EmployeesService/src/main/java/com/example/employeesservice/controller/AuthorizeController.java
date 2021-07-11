package com.example.employeesservice.controller;

import com.example.employeesservice.domain.Employee;
import com.example.employeesservice.dto.response.JwtParseRequestDTO;
import com.example.employeesservice.dto.response.JwtParseResponseDTO;
import com.example.employeesservice.repository.EmployeeRepository;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

import static com.example.employeesservice.utils.SecurityConstants.AUTHORIZATION_HEADER;
import static com.example.employeesservice.utils.SecurityConstants.BEARER_PREFIX;

public abstract class AuthorizeController {

    protected final Employee employee;

    public AuthorizeController(HttpServletRequest request, EmployeeRepository employeeRepository) {
        String token = request.getHeader(AUTHORIZATION_HEADER);
        token = token.replace(BEARER_PREFIX + " ", "");
        JwtParseResponseDTO response = parseJwt(token);

        this.employee = employeeRepository.findById(response.getUserId()).orElse(null);
    }

    private JwtParseResponseDTO parseJwt(String token) {
        RestTemplate restTemplate = new RestTemplate();

        String JWT_PARSE_URL = "http://localhost:8090/v1/jwt/parse";
        JwtParseResponseDTO responseDto = restTemplate.postForObject(JWT_PARSE_URL, new JwtParseRequestDTO(token),
                JwtParseResponseDTO.class);

        Objects.requireNonNull(responseDto);
        return responseDto;
    }
}
