package com.example.appealsservice.dto.response;


import com.example.appealsservice.domain.Appeal;
import com.example.appealsservice.domain.StatusAppeal;
import com.example.appealsservice.domain.Theme;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import java.util.Date;


public class AppealDto {

    private Long id;

    private Long client_id;

    private Theme theme;

    private String description;

    private Date createDate;

    private Date updateDate;

    private StatusAppeal statusAppeal;

    public AppealDto(Appeal appeal) {
        BeanUtils.copyProperties(appeal, this);
    }
}
