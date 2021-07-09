package com.example.appealsservice.dto.response;

import com.example.appealsservice.domain.Appeal;
import com.example.appealsservice.domain.StatusAppeal;
import com.example.appealsservice.domain.Theme;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ShortAppealDto {

    private Long id;

    private Long clientId;

    private Theme theme;

    private String description;

    private Date createDate;

    private StatusAppeal statusAppeal;


    public ShortAppealDto() {}

    public ShortAppealDto(Appeal appeal) {

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