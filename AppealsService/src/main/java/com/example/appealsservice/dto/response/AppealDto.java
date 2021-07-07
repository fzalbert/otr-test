package com.example.appealsservice.dto.response;


import com.example.appealsservice.domain.Appeal;
import com.example.appealsservice.domain.StatusAppeal;
import com.example.appealsservice.domain.Theme;
import lombok.*;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import java.util.Date;

@Getter
@Setter
public class AppealDto {

    private Long id;

    private Long clientId;

    private Theme theme;

    private String description;

    private Date createDate;

    private StatusAppeal statusAppeal;

    public AppealDto(Appeal appeal) {

        if(appeal == null)
            return;
        id = appeal.getId();
        clientId = appeal.getClientId();
        theme = appeal.getTheme();
        description = appeal.getDescription();
        createDate = appeal.getCreateDate();
        statusAppeal = appeal.getStatusAppeal();
    }
}
