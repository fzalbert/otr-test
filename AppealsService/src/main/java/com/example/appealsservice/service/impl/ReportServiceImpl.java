package com.example.appealsservice.service.impl;

import com.example.appealsservice.domain.Appeal;
import com.example.appealsservice.domain.Report;
import com.example.appealsservice.domain.Task;
import com.example.appealsservice.domain.enums.ReportStatus;
import com.example.appealsservice.domain.enums.StatusAppeal;
import com.example.appealsservice.domain.enums.TaskStatus;
import com.example.appealsservice.dto.response.ReportDto;
import com.example.appealsservice.exception.NotRightsException;
import com.example.appealsservice.exception.ResourceNotFoundException;
import com.example.appealsservice.exception.TemplateException;
import com.example.appealsservice.kafka.model.MessageType;
import com.example.appealsservice.kafka.model.ModelConvertor;
import com.example.appealsservice.kafka.model.ModelMessage;
import com.example.appealsservice.messaging.MessageSender;
import com.example.appealsservice.repository.AppealRepository;
import com.example.appealsservice.repository.ReportRepository;
import com.example.appealsservice.repository.TaskRepository;
import com.example.appealsservice.service.ReportService;
import com.fasterxml.jackson.core.JsonProcessingException;
import javassist.NotFoundException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Scope("prototype")
@Service
public class ReportServiceImpl implements ReportService {


    private final TaskRepository taskRepository;
    private final AppealRepository appealRepository;
    private final ReportRepository reportRepository;
    private final MessageSender msgSender;


    public ReportServiceImpl(AppealRepository appealRepository, MessageSender msgSender,
                             ReportRepository reportRepository,
                             TaskRepository taskRepository) {
        this.appealRepository = appealRepository;
        this.reportRepository = reportRepository;
        this.taskRepository = taskRepository;
        this.msgSender = msgSender;
    }

    /**
     * создание отчета и одобрение либо отклонение обращения
     */
    @Override
    public void approveOrReject(Long appealId, Long employeeId, Boolean isApprove, String text) throws JsonProcessingException {

        var appeal = appealRepository
                .findById(appealId)
                .orElseThrow();

        var task = taskRepository
                .getByAppealId(appeal.getId())
                .stream()
                .sorted(Comparator.comparing(Task::getDate, Comparator.reverseOrder()))
                .findFirst()
                .orElse(null);

        if (task == null)
            throw new ResourceNotFoundException(appealId);

        if (!task.getEmployeeId().equals(employeeId))
            throw new TemplateException("Задача занята");

        if (task.getTaskStatus() == TaskStatus.NEEDUPDATE || task.getTaskStatus() == TaskStatus.NEEDCHECK)
            throw new TemplateException("Обращение еще не рассмотрено");

        task.setEmployeeId(employeeId);

        var report = new Report();
        report.setCreateDate(new Date());
        report.setAppeal(task.getAppeal());
        report.setReportStatus(isApprove ? ReportStatus.SUCCESS : ReportStatus.REJECTED);
        report.setText(text);

        task.setOver(true);

        reportRepository.save(report);

        task.getAppeal().setStatusAppeal(isApprove ? StatusAppeal.SUCCESS : StatusAppeal.REJECT);

        task.getAppeal().setOver(true);

        appealRepository.save(task.getAppeal());


        String subject = isApprove ? "APPEAL APPROVED" : "APPEAL REJECTED";

        MessageType messageType = isApprove ? MessageType.ACCEPT : MessageType.REJECT;

        ModelMessage model = ModelConvertor.Convert(task.getAppeal().getEmail(),
                task.getAppeal().getNameOrg(), appealId.toString(), subject, messageType);
        msgSender.sendEmail(model);

    }

    /**
     * получить все отчеты
     */
    @Override
    public List<ReportDto> getAll() {
        return reportRepository
                .findAll()
                .stream()
                .sorted(Comparator.comparing(Report::getCreateDate, Comparator.reverseOrder()))
                .map(ReportDto::new)
                .collect(Collectors.toList());
    }

    /**
     * получить по id
     */
    @Override
    public ReportDto getById(Long id) {
        var report = reportRepository.findById(id).orElseThrow(()
                -> new TemplateException("Отчет не найден"));

        return new ReportDto(report);
    }

    /**
     * получение списка отчетов по выбранному статусу
     */
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
