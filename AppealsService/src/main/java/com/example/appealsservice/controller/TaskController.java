package com.example.appealsservice.controller;

import com.example.appealsservice.dto.response.TaskDto;
import com.example.appealsservice.exception.NotRightsException;
import com.example.appealsservice.httpModel.CheckUser;
import com.example.appealsservice.service.TaskService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Scope("prototype")
@RestController
@RequestMapping("tasks")
public class TaskController extends AuthorizeController{

    private final TaskService taskService;
    private final CheckUser checkUser;

    @Autowired
    public TaskController(TaskService taskService,CheckUser checkUser, HttpServletRequest request) {
        super(request);
        this.taskService = taskService;
        this.checkUser = checkUser;
    }

    @GetMapping("take")
    public void take(@RequestParam Long appealId) throws JsonProcessingException {
        if(!checkUser.isEmployee(userModel))
            throw new NotRightsException("No rights");

        taskService.takeTask(appealId, userModel.getId());

    }

    @GetMapping("appoint")
    public void appoint(@RequestParam Long appealId) throws JsonProcessingException {
        if(!checkUser.isEmployee(userModel))
            throw new NotRightsException("No rights");
        taskService.Appoint(userModel.getId(), appealId);

    }

    @GetMapping("by-id")
    public TaskDto byId(@RequestParam Long id) {
       return taskService.geById(id);
    }

}

