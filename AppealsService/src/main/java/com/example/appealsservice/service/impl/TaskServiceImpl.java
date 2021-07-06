package com.example.appealsservice.service.impl;

import com.example.appealsservice.domain.StatusAppeal;
import com.example.appealsservice.domain.Task;
import com.example.appealsservice.domain.Theme;
import com.example.appealsservice.dto.response.AppealDto;
import com.example.appealsservice.dto.response.TaskDto;
import com.example.appealsservice.exception.NotRightsException;
import com.example.appealsservice.exception.ResourceNotFoundException;
import com.example.appealsservice.repository.AppealRepository;
import com.example.appealsservice.repository.TaskRepository;
import com.example.appealsservice.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final AppealRepository appealRepository;

    public TaskServiceImpl(TaskRepository taskRepository, AppealRepository appealRepository)
    {
        this.taskRepository = taskRepository;
        this.appealRepository = appealRepository;
    }


    /** взять задачу */
    @Override
    public void takeTask(long appealId, long employeeId) {

        var appeal = appealRepository.findById(appealId).orElseThrow(()
                -> new ResourceNotFoundException(appealId));

        if(appeal.getStatusAppeal() != StatusAppeal.NotProcessed)
            throw new NotRightsException("the employee is already fulfilling appeal");

        var task = taskRepository
                .findAll()
                .stream()
                .filter(x -> x.getEmployeeId() == employeeId && x.getAppeal().getId() == appealId)
                .findFirst()
                .orElseThrow(()
                        -> new NotRightsException("task already created"));


        task = new Task();
        task.setEmployeeId(employeeId);
        task.setDate( new Date());
        task.setOver(false);
        task.setAppeal(appeal);

        taskRepository.save(task);

        appeal.setStatusAppeal(StatusAppeal.InProccesing);
        appealRepository.save(appeal);
    }

    /** получить список задач по id сотрудника */
    @Override
    public List<TaskDto> getTasksByEmployeeId(long employeeId) {

        return taskRepository
                .findAll()
                .stream()
                .filter(x -> x.getEmployeeId() == employeeId)
                .sorted(Comparator.comparing(Task::getDate, Comparator.reverseOrder()))
                .map(x -> new TaskDto(x, new AppealDto(x.getAppeal())))
                .collect(Collectors.toList());

    }


    /** получить задачу по id  */
    @Override
    public TaskDto geById(long id) {
        var task = taskRepository
                .findById(id).orElseThrow(()
                        -> new ResourceNotFoundException(id));

        return new TaskDto(task, new AppealDto(task.getAppeal()));
    }


    @Override
    public void Appoint(long employeeId, long appealId) {

        var appeal = appealRepository
                .findById(appealId).orElseThrow(()
                -> new ResourceNotFoundException(appealId));

        if(appeal.getStatusAppeal() != StatusAppeal.NotProcessed)
            throw new NotRightsException("the employee is already fulfilling appeal");

        var task = taskRepository
                .findAll()
                .stream()
                .filter(x -> x.getEmployeeId() == employeeId && x.getAppeal().getId() == appealId)
                .findFirst()
                .orElseThrow(()
                        -> new NotRightsException("task already created"));

        task = new Task();
        task.setEmployeeId(employeeId);
        task.setDate( new Date());
        task.setOver(false);
        task.setAppeal(appeal);

        taskRepository.save(task);

        appeal.setStatusAppeal(StatusAppeal.InProccesing);
        appealRepository.save(appeal);

    }

    @Override
    public void returnAppeal(long employeeId, long taskId) {

        var task = taskRepository
                .findById(taskId).orElseThrow(()
                        -> new ResourceNotFoundException(taskId));

        if(employeeId != task.getEmployeeId())
            throw new NotRightsException("");

        if(task.isOver())
            throw new NotRightsException("Task is over");

        task.getAppeal().setStatusAppeal(StatusAppeal.NeedUpdate);
        appealRepository.save(task.getAppeal());

    }

}
