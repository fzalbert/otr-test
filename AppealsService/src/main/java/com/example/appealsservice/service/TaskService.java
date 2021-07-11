package com.example.appealsservice.service;

import com.example.appealsservice.dto.response.AppealDto;
import com.example.appealsservice.dto.response.TaskDto;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface TaskService {

    void takeTask(Long appealId, Long employeeId) throws JsonProcessingException;

    List<TaskDto> getTasksByEmployeeId(Long employeeId);

    TaskDto geById(Long Id);

    //admin appoint appeals to employees
    void Appoint(Long employeeId, Long appealId) throws JsonProcessingException;



}
