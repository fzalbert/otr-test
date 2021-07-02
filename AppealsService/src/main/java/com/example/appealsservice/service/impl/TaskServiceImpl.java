package com.example.appealsservice.service.impl;

import com.example.appealsservice.domain.StatusAppeal;
import com.example.appealsservice.domain.Task;
import com.example.appealsservice.dto.response.AppealDto;
import com.example.appealsservice.exception.ResourceNotFoundException;
import com.example.appealsservice.repository.AppealRepository;
import com.example.appealsservice.repository.TaskRepository;
import com.example.appealsservice.service.TaskService;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final AppealRepository appealRepository;

    public TaskServiceImpl(TaskRepository taskRepository, AppealRepository appealRepository)
    {
        this.taskRepository = taskRepository;
        this.appealRepository = appealRepository;
    }

    @Override
    public void take(long appealId, long employeeId) {

        var appeal = appealRepository.findById(appealId).orElseThrow(()
                -> new ResourceNotFoundException(appealId));

        var task = new Task();
        task.setEmployeeId(employeeId);
        task.setDate( new Date());
        task.setOver(false);
        task.setAppeal(appeal);

        taskRepository.save(task);

        appeal.setStatusAppeal(StatusAppeal.InProccesing);
        appealRepository.save(appeal);
    }

    @Override
    public List<AppealDto> getMyTasks(long employeeId) {
        return taskRepository
                .findAll()
                .stream()
                .filter(x -> x.getEmployeeId() == employeeId)
                .map(Task::getAppeal)
                .map(AppealDto::new)
                .collect(Collectors.toList());

    }

    @Override
    public void Appoint(long employeeId, long appealId) {

        var appeal = appealRepository.findById(appealId).orElseThrow(()
                -> new ResourceNotFoundException(appealId));

        var task = new Task();
        task.setEmployeeId(employeeId);
        task.setDate( new Date());
        task.setOver(false);
        task.setAppeal(appeal);

        taskRepository.save(task);

        appeal.setStatusAppeal(StatusAppeal.InProccesing);
        appealRepository.save(appeal);

    }
}
