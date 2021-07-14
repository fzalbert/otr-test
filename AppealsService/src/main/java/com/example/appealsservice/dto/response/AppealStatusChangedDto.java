package com.example.appealsservice.dto.response;

import com.example.appealsservice.domain.enums.TaskStatus;
import lombok.Data;

@Data
public class AppealStatusChangedDto {
    private ShortAppealDto appeal;
    private TaskStatus taskStatus;

    public AppealStatusChangedDto(ShortAppealDto appeal, TaskStatus taskStatus)
    {
        this.appeal = appeal;
        this.taskStatus = taskStatus;
    }
}
