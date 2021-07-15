package com.example.employeesservice.dto.response;

import com.example.employeesservice.domain.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ShortEmployeeDto implements Serializable {

    public Long id;
    public String lastName;

    public ShortEmployeeDto(Employee employee) {
        BeanUtils.copyProperties(employee, this);
    }
}
