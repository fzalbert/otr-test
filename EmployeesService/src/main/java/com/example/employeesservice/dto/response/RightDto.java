package com.example.employeesservice.dto.response;

import com.example.employeesservice.domain.Right;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class RightDto {

    private Long id;
    private String title;

    public RightDto(Right right) {
        BeanUtils.copyProperties(right, this);
    }
}
