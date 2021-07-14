package com.example.appealsservice.service.impl;

import com.example.appealsservice.domain.enums.StatusAppeal;
import com.example.appealsservice.domain.Task;
import com.example.appealsservice.domain.enums.TaskStatus;
import com.example.appealsservice.dto.response.ShortAppealDto;
import com.example.appealsservice.dto.response.TaskDto;
import com.example.appealsservice.exception.NotRightsException;
import com.example.appealsservice.exception.ResourceNotFoundException;
import com.example.appealsservice.kafka.model.MessageType;
import com.example.appealsservice.kafka.model.ModelConvertor;
import com.example.appealsservice.kafka.model.ModelMessage;
import com.example.appealsservice.repository.AppealRepository;
import com.example.appealsservice.repository.TaskRepository;
import com.example.appealsservice.service.TaskService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Scope("prototype")
@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final AppealRepository appealRepository;

    public TaskServiceImpl(TaskRepository taskRepository, AppealRepository appealRepository)
    {
        this.taskRepository = taskRepository;
        this.appealRepository = appealRepository;
    }


    /**
     * взять задачу
     */
    @Override
    public void takeTask(Long appealId, Long employeeId, TaskStatus status) {

        var appeal = appealRepository.findById(appealId).orElseThrow(()
                -> new ResourceNotFoundException(appealId));

        if(appeal.isOver())
            throw new NotRightsException("Appeal is over");

        var task = taskRepository
                .getByAppealId(appealId)
                .stream()
                .sorted(Comparator.comparing(Task::getDate, Comparator.reverseOrder()))
                .findFirst()
                .get();

        if(task.getEmployeeId() != null)
            throw new NotRightsException("Task already busy");

        task.setTaskStatus(status);
        task.setEmployeeId(employeeId);
        taskRepository.save(task);

    }


    /**
     * получить список задач по id сотрудника
     */
    @Override
    public List<TaskDto> getTasksByEmployeeId(Long employeeId) {

        return taskRepository
                .findAll()
                .stream()
                .filter(x -> x.getEmployeeId() == employeeId)
                .sorted(Comparator.comparing(Task::getDate, Comparator.reverseOrder()))
                .map(x -> new TaskDto(x, new ShortAppealDto(x.getAppeal())))
                .collect(Collectors.toList());

    }


    /**
     * получить задачу по id
     */
    @Override
    public TaskDto geById(Long id) {
        var task = taskRepository
                .findById(id).orElseThrow(()
                        -> new ResourceNotFoundException(id));

        return new TaskDto(task, new ShortAppealDto(task.getAppeal()));
    }


    /**
     * создать задачу для другого сотрудника
     */
    @Override
    public void Appoint(Long employeeId, Long appealId) throws JsonProcessingException {

        var appeal = appealRepository
                .findById(appealId).orElseThrow(()
                         -> new ResourceNotFoundException(appealId));

        var task = taskRepository
                .getByAppealId(appealId)
                .stream()
                .sorted(Comparator.comparing(Task::getDate, Comparator.reverseOrder()))
                .findFirst()
                .get();

        if(task.getEmployeeId() != null)
            throw new NotRightsException("Task already busy");

        task.setEmployeeId(employeeId);

        taskRepository.save(task);

    }

}
