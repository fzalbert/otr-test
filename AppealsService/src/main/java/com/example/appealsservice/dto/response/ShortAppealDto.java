package com.example.appealsservice.dto.response;

import com.example.appealsservice.domain.Appeal;
import com.example.appealsservice.domain.Task;
import com.example.appealsservice.domain.enums.StatusAppeal;
import com.example.appealsservice.domain.Theme;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.kafka.common.protocol.types.Field;

import java.util.Date;

@Data
public class ShortAppealDto {

    private Long id;

    private Long employeeId;

    private String nameOrg;

    private Theme theme;

    private String description;

    private Date createDate;

    private Integer statusAppeal;


    public ShortAppealDto() {}

    public ShortAppealDto(Appeal appeal) {

        if(appeal == null)
            return;
        id = appeal.getId();
        nameOrg = appeal.getNameOrg();
        theme = appeal.getTheme();
        description = appeal.getDescription();
        createDate = appeal.getCreateDate();
        statusAppeal = appeal.getStatusAppeal().getValue();

    }

    public ShortAppealDto(Appeal appeal, Task task) {

        if(appeal == null)
            return;
        id = appeal.getId();
        if (task != null)
            this.employeeId = task.getEmployeeId();
        nameOrg = appeal.getNameOrg();
        theme = appeal.getTheme();
        description = appeal.getDescription();
        createDate = appeal.getCreateDate();
        statusAppeal = appeal.getStatusAppeal().getValue();

    }
}
