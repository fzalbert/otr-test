package com.example.appealsservice.service;

import com.example.appealsservice.dto.response.AppealDto;
import com.example.appealsservice.dto.response.TaskDto;

import java.util.List;

public interface TaskService {

    void takeTask(long appealId, long employeeId);

    List<TaskDto> getTasksByEmployeeId(long employeeId);

    //admin appoint appeals to employees
    void Appoint(long employeeId, long appealId);

    void returnAppeal(long employeeId, long taskId);
}
