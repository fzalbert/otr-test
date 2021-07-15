package com.example.employeesservice.dto.response;

import com.example.employeesservice.domain.Role;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class RoleDto {

    private Long id;
    private String title;

    public RoleDto(Role role) {
        BeanUtils.copyProperties(role, this);
    }
}
