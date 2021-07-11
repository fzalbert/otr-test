package com.example.appealsservice.controller;

import com.example.appealsservice.dto.response.TaskDto;
import com.example.appealsservice.service.TaskService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Scope("prototype")
@RestController
@RequestMapping("tasks")
public class TaskController extends AuthorizeController{

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService, HttpServletRequest request) {
        super(request);
        this.taskService = taskService;
    }

    @GetMapping("take")
    public void take(Long appealId) throws JsonProcessingException {
        taskService.takeTask(appealId, employeeModel.getId());

    }

    @GetMapping("appoint")
    public void appoint( Long appealId) throws JsonProcessingException {
        taskService.Appoint(employeeModel.getId(), appealId);

    }

    @GetMapping("{id}")
    public TaskDto byId(Long id) {
       return taskService.geById(id);
    }

}

