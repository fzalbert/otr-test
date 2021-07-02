package com.example.employeesservice.dto.response;

import com.example.employeesservice.domain.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
@AllArgsConstructor
public class ShortEmployeeDTO {

    public Long id;
    public String lastName;

    public ShortEmployeeDTO(Employee employee) {
        BeanUtils.copyProperties(employee, this);
    }
}
