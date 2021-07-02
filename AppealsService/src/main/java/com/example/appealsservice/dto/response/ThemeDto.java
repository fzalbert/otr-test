package com.example.appealsservice.dto.response;

import com.example.appealsservice.domain.Theme;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThemeDto {

    private Long id;
    private String Name;

    public ThemeDto(Theme theme) {
        BeanUtils.copyProperties(theme, this);
    }
}
