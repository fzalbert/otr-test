package com.example.autorizeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseModel{
    public HttpStatus status = HttpStatus.OK;
    public String content;
}
