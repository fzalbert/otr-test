package com.example.appealsservice.service;

import com.example.appealsservice.dto.response.AppealDto;

import java.util.List;

public interface TaskService {
    void take(long appealId, long employeeId);
    List<AppealDto> getMyTasks(long employeeId);

    //admin appoint appeals to employees
    void Appoint(long employeeId, long appealId);
}
