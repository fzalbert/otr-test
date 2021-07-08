package com.example.appealsservice.service.impl;

import com.example.appealsservice.domain.StatusAppeal;
import com.example.appealsservice.domain.Task;
import com.example.appealsservice.domain.Theme;
import com.example.appealsservice.dto.response.AppealDto;
import com.example.appealsservice.dto.response.ShortAppealDto;
import com.example.appealsservice.dto.response.TaskDto;
import com.example.appealsservice.exception.NotRightsException;
import com.example.appealsservice.exception.ResourceNotFoundException;
import com.example.appealsservice.kafka.kafkamsg.ProducerApacheKafkaMsgSender;
import com.example.appealsservice.kafka.model.MessageType;
import com.example.appealsservice.kafka.model.ModelConvertor;
import com.example.appealsservice.kafka.model.ModelMessage;
import com.example.appealsservice.repository.AppealRepository;
import com.example.appealsservice.repository.TaskRepository;
import com.example.appealsservice.service.TaskService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ProducerApacheKafkaMsgSender apacheKafkaMsgSender;
    private final AppealRepository appealRepository;

    public TaskServiceImpl(TaskRepository taskRepository, AppealRepository appealRepository,
                           ProducerApacheKafkaMsgSender apacheKafkaMsgSender)
    {
        this.taskRepository = taskRepository;
        this.appealRepository = appealRepository;
        this.apacheKafkaMsgSender = apacheKafkaMsgSender;
    }


    /** взять задачу */
    @Override
    public void takeTask(long appealId, long employeeId) throws JsonProcessingException {

        var appeal = appealRepository.findById(appealId).orElseThrow(()
                -> new ResourceNotFoundException(appealId));

        if (appeal.getStatusAppeal() != StatusAppeal.NotProcessed)
            throw new NotRightsException("the employee is already fulfilling appeal");

        var task = taskRepository
                .findAll()
                .stream()
                .filter(x -> x.getEmployeeId() == employeeId && x.getAppeal().getId() == appealId)
                .findFirst();

        if (task.isPresent())
            throw new NotRightsException("task already created");


        var taskDb = new Task();
        taskDb.setEmployeeId(employeeId);
        taskDb.setDate(new Date());
        taskDb.setOver(false);
        taskDb.setAppeal(appeal);

        taskRepository.save(taskDb);

        appeal.setStatusAppeal(StatusAppeal.InProccesing);
        appealRepository.save(appeal);

        ModelMessage model = ModelConvertor.Convert(appeal.getEmail(),
                appeal.getNameClient(), "APPEAL TAKEN FOR CONSIDERATION ", MessageType.TakeAppeal);

        apacheKafkaMsgSender.initializeKafkaProducer();
        apacheKafkaMsgSender.sendJson(model);
    }

    /** получить список задач по id сотрудника */
    @Override
    public List<TaskDto> getTasksByEmployeeId(long employeeId) {

        return taskRepository
                .findAll()
                .stream()
                .filter(x -> x.getEmployeeId() == employeeId)
                .sorted(Comparator.comparing(Task::getDate, Comparator.reverseOrder()))
                .map(x -> new TaskDto(x, new ShortAppealDto(x.getAppeal())))
                .collect(Collectors.toList());

    }


    /** получить задачу по id  */
    @Override
    public TaskDto geById(long id) {
        var task = taskRepository
                .findById(id).orElseThrow(()
                        -> new ResourceNotFoundException(id));

        return new TaskDto(task, new ShortAppealDto(task.getAppeal()));
    }


    @Override
    public void Appoint(long employeeId, long appealId) throws JsonProcessingException {

        var appeal = appealRepository
                .findById(appealId).orElseThrow(()
                -> new ResourceNotFoundException(appealId));

        if(appeal.getStatusAppeal() != StatusAppeal.NotProcessed)
            throw new NotRightsException("the employee is already fulfilling appeal");

        var task = taskRepository
                .findAll()
                .stream()
                .filter(x -> x.getEmployeeId() == employeeId && x.getAppeal().getId() == appealId)
                .findFirst();

        if (task.isPresent())
            throw new NotRightsException("task already created");

        var taskDb = new Task();
        taskDb.setEmployeeId(employeeId);
        taskDb.setDate( new Date());
        taskDb.setOver(false);
        taskDb.setAppeal(appeal);

        taskRepository.save(taskDb);

        appeal.setStatusAppeal(StatusAppeal.InProccesing);
        appealRepository.save(appeal);

        ModelMessage model = ModelConvertor.Convert(appeal.getEmail(),
                appeal.getNameClient(), "APPEAL TAKEN FOR CONSIDERATION", MessageType.TakeAppeal);

        apacheKafkaMsgSender.initializeKafkaProducer();
        apacheKafkaMsgSender.sendJson(model);

    }

    @Override
    public void returnAppeal(long employeeId, long taskId) throws JsonProcessingException {

        var task = taskRepository
                .findById(taskId).orElseThrow(()
                        -> new ResourceNotFoundException(taskId));

        if(employeeId != task.getEmployeeId())
            throw new NotRightsException("");

        if(task.isOver())
            throw new NotRightsException("Task is over");

        task.getAppeal().setStatusAppeal(StatusAppeal.NeedUpdate);
        appealRepository.save(task.getAppeal());

        ModelMessage model = ModelConvertor.Convert(task.getAppeal().getEmail(),
                task.getAppeal().getNameClient(), "APPEAL NEED UPDATE", MessageType.NeedUpdate);

        apacheKafkaMsgSender.initializeKafkaProducer();
        apacheKafkaMsgSender.sendJson(model);

    }

}
