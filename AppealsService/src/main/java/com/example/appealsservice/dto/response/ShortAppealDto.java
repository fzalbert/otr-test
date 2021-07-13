package com.example.appealsservice.dto.response;

import com.example.appealsservice.domain.Appeal;
import com.example.appealsservice.domain.enums.StatusAppeal;
import com.example.appealsservice.domain.Theme;
import lombok.Getter;
import lombok.Setter;
import org.apache.kafka.common.protocol.types.Field;

import java.util.Date;

@Getter
@Setter
public class ShortAppealDto {

    private Long id;

    private Long clientId;

    private Theme theme;

    private String description;

    private Date createDate;

    private StatusAppeal statusAppeal;

    private Long employeeId;


    public ShortAppealDto() {}

    public ShortAppealDto(Appeal appeal ) {

        if(appeal == null)
            return;
        id = appeal.getId();
        clientId = appeal.getClientId();
        employeeId = appeal.getEmployeeId();
        theme = appeal.getTheme();
        description = appeal.getDescription();
        createDate = appeal.getCreateDate();
        statusAppeal = appeal.getStatusAppeal();

    }
}
