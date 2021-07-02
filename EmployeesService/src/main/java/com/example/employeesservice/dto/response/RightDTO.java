package com.example.employeesservice.dto.response;

import com.example.employeesservice.domain.Right;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class RightDTO {

    private Long id;
    private String title;

    public RightDTO(Right right) {
        BeanUtils.copyProperties(right, this);
    }
}
