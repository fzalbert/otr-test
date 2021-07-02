package com.example.employeesservice.dto.response;

import com.example.employeesservice.domain.Role;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class RoleDTO {

    private Long id;
    private String title;

    public RoleDTO(Role role) {
        BeanUtils.copyProperties(role, this);
    }
}
