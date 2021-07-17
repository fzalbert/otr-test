package com.example.appealsservice.dto.response;

import com.example.appealsservice.domain.Appeal;
import com.example.appealsservice.domain.Theme;
import com.example.appealsservice.domain.enums.TaskStatus;
import com.fasterxml.jackson.databind.util.BeanUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppealStatusChangedDto implements Serializable {
    private Long id;
    private Long employeeId;
    private String nameOrg;
    private String description;
    private Theme theme;
    private Date createDate;
    private Integer statusAppeal;
    private String taskStatus;

    public AppealStatusChangedDto(Appeal appeal, String taskStatus){
        BeanUtils.copyProperties(appeal, this);
        this.statusAppeal = appeal.getStatusAppeal().getValue();
        this.taskStatus = taskStatus;
        this.theme = appeal.getTheme();
    }
}
