package com.example.appealsservice.controller;

import com.example.appealsservice.dto.enums.UserType;
import com.example.appealsservice.dto.request.JwtParseRequestDto;
import com.example.appealsservice.dto.response.JwtParseResponseDto;
import com.example.appealsservice.exception.AuthorizeException;
import com.example.appealsservice.httpModel.ClientModel;
import com.example.appealsservice.httpModel.EmployeeModel;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public abstract class AuthorizeController {

    private final String HEADER = "Authorization";

    private final String HEADER_VALUE_PREFIX = "Bearer";
    private final RestTemplate restTemplate = new RestTemplate();
    private final String JWT_PARSE_URL = "http://localhost:8090/v1/jwt/parse";
    protected ClientModel clientModel;
    protected EmployeeModel employeeModel;

    public AuthorizeController(HttpServletRequest request) {
        String token = request.getHeader(HEADER);
        token = token.replace(HEADER_VALUE_PREFIX + " ", "");
        JwtParseResponseDto response = parseJwt(token);

        if(response == null)
            throw new AuthorizeException();

        if (response.getUserType() == UserType.CLIENT)
            clientModel = getClient(response.getUserId());

        if (response.getUserType() == UserType.EMPLOYEE)
            employeeModel = getEmployee(response.getUserId());
    }

    private JwtParseResponseDto parseJwt(String token) {
        JwtParseResponseDto responseDto = restTemplate.postForObject(JWT_PARSE_URL, new JwtParseRequestDto(token),
                JwtParseResponseDto.class);

        Objects.requireNonNull(responseDto);
        return responseDto;
    }

    private ClientModel getClient(long id){
        return restTemplate.postForObject(JWT_PARSE_URL, id,
                ClientModel.class);
    }

    private EmployeeModel getEmployee(long id){
        return restTemplate.postForObject(JWT_PARSE_URL, id,
                EmployeeModel.class);
    }
}

