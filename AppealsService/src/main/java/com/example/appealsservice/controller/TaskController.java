package com.example.appealsservice.controller;

import com.example.appealsservice.dto.response.TaskDto;
import com.example.appealsservice.exception.NotRightsException;
import com.example.appealsservice.httpModel.CheckUser;
import com.example.appealsservice.service.TaskService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Log4j
@Scope("prototype")
@RestController
@RequestMapping("tasks")
public class TaskController extends AuthorizeController{

    private final TaskService taskService;
    private final CheckUser checkUser;

    @Autowired
    public TaskController(TaskService taskService,CheckUser checkUser, HttpServletRequest request,
                          @Value("${security.jwt.parse.url}") String jwtParseUrl) {
        super(request, jwtParseUrl);
        this.taskService = taskService;
        this.checkUser = checkUser;
    }

    /**
     * Взять задачу
     * @param appealId
     */
    @GetMapping("take")
    public void take(@RequestParam Long appealId) throws JsonProcessingException {

        log.debug("Request method tasks/take for AppealId= " + appealId );
        if(!checkUser.isEmployee(userModel))
            throw new NotRightsException("Нет прав");

        try {
            log.debug("SUCCESS Request method tasks/take for AppealId= " + appealId);
            taskService.takeTask(appealId, userModel.getId());
        }
        catch (Exception e)
        {
            log.error("ERROR Request method tasks/take for AppealId= " + appealId);
        }

    }

    /**
     * Назначить сотрудника на задачу
     * @param appealId
     * @param employeeId
     */
    @GetMapping("appoint")
    public void appoint(@RequestParam Long appealId, @RequestParam Long employeeId) throws JsonProcessingException {
        if(!checkUser.isAdmin(userModel))
            throw new NotRightsException("Нет прав");
        log.debug("Request method tasks/take for AppealId= " + appealId );
        taskService.appoint(employeeId, appealId);

    }

    /**
     * Получить таск по id
     * @param id
     */
    @GetMapping("by-id")
    public TaskDto byId(@RequestParam Long id) {

       return taskService.geById(id);
    }

}

