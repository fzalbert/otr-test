package com.example.appealsservice.dto.response;

import com.example.appealsservice.domain.Task;

import java.util.Date;

public class TaskDto {

    private Long id;

    private Long appealId;

    private Long employeeId;

    private Integer taskStatus;

    private boolean isOver;

    private Date date;

    public TaskDto()
    {

    }
    public TaskDto(Task task)
    {
        if (task == null)
            return;
        id = task.getId();
        appealId = task.getAppeal().getId();
        taskStatus = task.getTaskStatus().getValue();
        employeeId = task.getEmployeeId();
        isOver = task.isOver();
        date = task.getDate();
    }
}
