package com.example.appealsservice.dto.response;

import com.example.appealsservice.domain.Task;

import java.util.Date;

public class TaskDto {

    private Long id;

    private ShortAppealDto appealDto;

    private boolean isOver;

    private Date date;

    public TaskDto()
    {

    }
    public TaskDto(Task task,ShortAppealDto appeal)
    {
        if (task == null)
            return;
        id = task.getId();
        appealDto = appeal;
        isOver = task.isOver();
        date = task.getDate();
    }
}
