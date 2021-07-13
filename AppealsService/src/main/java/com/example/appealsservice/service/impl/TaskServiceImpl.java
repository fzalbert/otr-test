package com.example.appealsservice.service.impl;

import com.example.appealsservice.domain.enums.StatusAppeal;
import com.example.appealsservice.domain.Task;
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


    /**
     * взять задачу
     */
    @Override
    public void takeTask(Long appealId, Long employeeId) throws JsonProcessingException {

        var appeal = appealRepository.findById(appealId).orElseThrow(()
                -> new ResourceNotFoundException(appealId));

        if (appeal.getStatusAppeal() != StatusAppeal.NOTPROCCESING)
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

        appeal.setStatusAppeal(StatusAppeal.INPROCCESING);
        appealRepository.save(appeal);

        ModelMessage model = ModelConvertor.Convert(appeal.getEmail(),
                appeal.getNameOrg(), "APPEAL TAKEN FOR CONSIDERATION ", MessageType.TAKEAPPEAL);

        apacheKafkaMsgSender.initializeKafkaProducer();
        apacheKafkaMsgSender.sendJson(model);
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

        if(appeal.getStatusAppeal() != StatusAppeal.NOTPROCCESING)
            throw new NotRightsException("The employee is already fulfilling appeal");

        var task = taskRepository
                .findAll()
                .stream()
                .filter(x -> x.getEmployeeId() == employeeId && x.getAppeal().getId() == appealId)
                .findFirst();

        if (task.isPresent())
            throw new NotRightsException("Task already created");

        var taskDb = new Task();
        taskDb.setEmployeeId(employeeId);
        taskDb.setDate( new Date());
        taskDb.setOver(false);
        taskDb.setAppeal(appeal);

        taskRepository.save(taskDb);

        appeal.setStatusAppeal(StatusAppeal.INPROCCESING);
        appealRepository.save(appeal);

        ModelMessage model = ModelConvertor.Convert(appeal.getEmail(),
                appeal.getNameOrg(), "APPEAL TAKEN FOR CONSIDERATION", MessageType.TAKEAPPEAL);

        apacheKafkaMsgSender.initializeKafkaProducer();
        apacheKafkaMsgSender.sendJson(model);

    }

}
