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

        var temp = request.getHeader("id");
        var temp2 = request.getHeader(AUTHORIZATION_HEADER);
        Long employeeId = Long.parseLong(request.getHeaders("id").nextElement());
        this.employee = employeeRepository.findById(employeeId).orElse(null);
    }
}
