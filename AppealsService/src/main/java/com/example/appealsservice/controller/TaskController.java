package com.example.appealsservice.controller;

import com.example.appealsservice.dto.response.TaskDto;
import com.example.appealsservice.service.impl.TaskServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("tasks")
public class TaskController extends AuthorizeController{

    private final TaskServiceImpl taskServiceImpl;

    @Autowired
    public TaskController(TaskServiceImpl taskServiceImpl, HttpServletRequest request) {
        super(request);
        this.taskServiceImpl = taskServiceImpl;
    }

    @GetMapping("take")
    public void take(Long appealId) throws JsonProcessingException {
        taskServiceImpl.takeTask(appealId, employeeModel.getId());

    }

    @GetMapping("appoint")
    public void appoint( Long appealId) throws JsonProcessingException {
        taskServiceImpl.Appoint(employeeModel.getId(), appealId);

    }

    @GetMapping("{id}")
    public TaskDto byId(Long id) {
       return taskServiceImpl.geById(id);
    }

}

