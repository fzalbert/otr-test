package com.example.appealsservice.dto.response;

import com.example.appealsservice.domain.enums.TaskStatus;
import lombok.Data;

@Data
public class AppealStatusChangedDto {
    private Long appealId;
    private TaskStatus taskStatus;

    public AppealStatusChangedDto(Long appealId, TaskStatus taskStatus)
    {
        this.appealId = appealId;
        this.taskStatus = taskStatus;
    }
}
