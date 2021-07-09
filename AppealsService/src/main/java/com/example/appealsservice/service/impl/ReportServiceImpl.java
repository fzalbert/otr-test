package com.example.appealsservice.service.impl;

import com.example.appealsservice.domain.Report;
import com.example.appealsservice.domain.ReportStatus;
import com.example.appealsservice.domain.StatusAppeal;
import com.example.appealsservice.dto.response.ReportDto;
import com.example.appealsservice.exception.NotRightsException;
import com.example.appealsservice.exception.ResourceNotFoundException;
import com.example.appealsservice.kafka.kafkamsg.ProducerApacheKafkaMsgSender;
import com.example.appealsservice.kafka.model.MessageType;
import com.example.appealsservice.kafka.model.ModelConvertor;
import com.example.appealsservice.kafka.model.ModelMessage;
import com.example.appealsservice.repository.AppealRepository;
import com.example.appealsservice.repository.ReportRepository;
import com.example.appealsservice.repository.TaskRepository;
import com.example.appealsservice.service.ReportService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {


    private final TaskRepository taskRepository;
    private final AppealRepository appealRepository;
    private final ReportRepository reportRepository;
    private final ProducerApacheKafkaMsgSender apacheKafkaMsgSender;


    public ReportServiceImpl(AppealRepository appealRepository, ReportRepository reportRepository,
                             TaskRepository taskRepository, ProducerApacheKafkaMsgSender apacheKafkaMsgSender) {
        this.appealRepository = appealRepository;
        this.reportRepository = reportRepository;
        this.apacheKafkaMsgSender = apacheKafkaMsgSender;
        this.taskRepository = taskRepository;
    }

    /** создание отчета и одобрение обращения  */
    @Override
    public void approve(long taskId, long employeeId, String text) throws JsonProcessingException {

        var task = taskRepository.findById(taskId).orElseThrow(()
                -> new ResourceNotFoundException(taskId));

        if(task.getEmployeeId() != employeeId)
            throw  new NotRightsException("This task is not yours");

        if(task.isOver())
            throw new NotRightsException("Task already over");

        var report = new Report();
        report.setCreateDate(new Date());
        report.setAppeal(task.getAppeal());
        report.setReportStatus(ReportStatus.Success);
        report.setText(text);

        task.setOver(true);

        reportRepository.save(report);

        task.getAppeal().setStatusAppeal(StatusAppeal.Success);
        appealRepository.save(task.getAppeal());

        ModelMessage model = ModelConvertor.Convert(task.getAppeal().getEmail(),
                task.getAppeal().getNameClient(), "APPEAL APPROVED", MessageType.Accept);

        apacheKafkaMsgSender.initializeKafkaProducer();
        apacheKafkaMsgSender.sendJson(model);

    }

    /** создание отчета и отклонение обращения  */
    @Override
    public void reject(long taskId, long employeeId, String text) throws JsonProcessingException {

        var task = taskRepository.findById(taskId).orElseThrow(()
                -> new ResourceNotFoundException(taskId));

        if(task.getEmployeeId() != employeeId)
            throw  new NotRightsException("This task is not yours");

        var report = new Report();
        report.setCreateDate(new Date());
        report.setReportStatus(ReportStatus.Rejected);
        report.setText(text);

        task.setOver(true);

        reportRepository.save(report);

        task.getAppeal().setStatusAppeal(StatusAppeal.Rejected);
        appealRepository.save(task.getAppeal());

        ModelMessage model = ModelConvertor.Convert(task.getAppeal().getEmail(),
                task.getAppeal().getNameClient(), "APPEAL DECLINED ", MessageType.Reject);

        apacheKafkaMsgSender.initializeKafkaProducer();
        apacheKafkaMsgSender.sendJson(model);
    }

    /** получить все отчеты  */
    @Override
    public List<ReportDto> getAll() {
        return reportRepository
                .findAll()
                .stream()
                .sorted(Comparator.comparing(Report::getCreateDate, Comparator.reverseOrder()))
                .map(ReportDto::new)
                .collect(Collectors.toList());
    }

    /** получить по id */
    @Override
    public ReportDto getById(long id) {
        var report = reportRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException(id));

        return new ReportDto(report);
    }

    /** получение списка отчетов по выбранному статусу  */
    @Override
    public List<ReportDto> getByStatus(ReportStatus status) {
        return reportRepository
                .findAll()
                .stream()
                .filter(x -> x.getReportStatus() == status)
                .sorted(Comparator.comparing(Report::getCreateDate, Comparator.reverseOrder()))
                .map(ReportDto::new)
                .collect(Collectors.toList());
    }
}
