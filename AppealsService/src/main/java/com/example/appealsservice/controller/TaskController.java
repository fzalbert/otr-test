package com.example.appealsservice.controller;

import com.example.appealsservice.dto.response.ReportDto;
import com.example.appealsservice.dto.response.TaskDto;
import com.example.appealsservice.service.impl.TaskServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("tasks")
public class TaskController {

    private final TaskServiceImpl taskServiceImpl;

    @Autowired
    public TaskController(TaskServiceImpl taskServiceImpl) {
        this.taskServiceImpl = taskServiceImpl;
    }

    @GetMapping("take")
    public void take(long appealId, long employeeId) throws JsonProcessingException {
        taskServiceImpl.takeTask(appealId, employeeId);

    }

    @GetMapping("appoint")
    public void appoint(long employeeId, long appealId) throws JsonProcessingException {
        taskServiceImpl.Appoint(employeeId, appealId);

    }

    @GetMapping("{id}")
    public TaskDto byId(long id) {
       return taskServiceImpl.geById(id);
    }

}

