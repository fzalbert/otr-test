package com.example.appealsservice.service.impl;

import com.example.appealsservice.domain.enums.StatusAppeal;
import com.example.appealsservice.domain.Task;
import com.example.appealsservice.domain.enums.TaskStatus;
import com.example.appealsservice.dto.response.ShortAppealDto;
import com.example.appealsservice.dto.response.TaskDto;
import com.example.appealsservice.exception.MissingRequiredFieldException;
import com.example.appealsservice.exception.NotRightsException;
import com.example.appealsservice.exception.ResourceNotFoundException;
import com.example.appealsservice.exception.TemplateException;
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

    public TaskServiceImpl(TaskRepository taskRepository, AppealRepository appealRepository) {
        this.taskRepository = taskRepository;
        this.appealRepository = appealRepository;
    }


    /**
     * взять задачу
     */
    @Override
    public void takeTask(Long appealId, Long employeeId) {

        var appeal = appealRepository.findById(appealId).orElseThrow(()
                -> new ResourceNotFoundException(appealId));

        if (appeal.isOver())
            throw new TemplateException("Обращение выполнено");

        var task = taskRepository
                .getByAppealId(appealId)
                .stream()
                .sorted(Comparator.comparing(Task::getDate, Comparator.reverseOrder()))
                .findFirst()
                .orElse(null);

        if (task.getEmployeeId() != null)
            throw new TemplateException("Задача занята");

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
                .map(TaskDto::new)
                .collect(Collectors.toList());

    }


    /**
     * получить задачу по id
     */
    @Override
    public TaskDto geById(Long id) {
        var task = taskRepository
                .findById(id).orElseThrow(() -> new TemplateException("Задача не найдена"));

        return new TaskDto(task);
    }


    /**
     * создать задачу для другого сотрудника
     */
    @Override
    public void appoint(Long employeeId, Long appealId) throws JsonProcessingException {

        var appeal = appealRepository
                .findById(appealId).orElseThrow(()
                        -> new ResourceNotFoundException(appealId));

        var task = taskRepository
                .getByAppealId(appealId)
                .stream()
                .sorted(Comparator.comparing(Task::getDate, Comparator.reverseOrder()))
                .findFirst()
                .orElseThrow(() -> new TemplateException("Задача не найдена"));

        if (task.getEmployeeId() != null)
            throw new TemplateException("Задача занята");

        task.setEmployeeId(employeeId);

        taskRepository.save(task);

    }

}
