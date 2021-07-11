package com.example.employeesservice.dto.response;

import com.example.employeesservice.domain.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ShortEmployeeDTO implements Serializable {

    public Long id;
    public String lastName;

    public ShortEmployeeDTO(Employee employee) {
        BeanUtils.copyProperties(employee, this);
    }
}
