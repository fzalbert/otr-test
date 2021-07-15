package com.example.employeesservice.dto.response;

import com.example.employeesservice.domain.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ShortEmployeeDto implements Serializable {

    private Long id;
    private String lastName;

    public ShortEmployeeDto(Employee employee) {
        BeanUtils.copyProperties(employee, this);
    }
}
