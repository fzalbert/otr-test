package com.example.appealsservice.service.impl;

import com.example.appealsservice.domain.Report;
import com.example.appealsservice.domain.ReportStatus;
import com.example.appealsservice.domain.StatusAppeal;
import com.example.appealsservice.dto.response.ReportDto;
import com.example.appealsservice.exception.NotRightsException;
import com.example.appealsservice.exception.ResourceNotFoundException;
import com.example.appealsservice.repository.AppealRepository;
import com.example.appealsservice.repository.ReportRepository;
import com.example.appealsservice.repository.TaskRepository;
import com.example.appealsservice.service.ReportService;
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

    public ReportServiceImpl(AppealRepository appealRepository, ReportRepository reportRepository,
                             TaskRepository taskRepository) {
        this.appealRepository = appealRepository;
        this.reportRepository = reportRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public void approve(long taskId, long employeeId, String text) {

        var task = taskRepository.findById(taskId).orElseThrow(()
                -> new ResourceNotFoundException(taskId));

        if(task.getEmployeeId() != employeeId)
            throw  new NotRightsException("");

        var report = new Report();
        report.setCreateDate(new Date());
        report.setReportStatus(ReportStatus.Success);
        report.setText(text);

        task.setOver(true);

        reportRepository.save(report);

        task.getAppeal().setStatusAppeal(StatusAppeal.Success);
        appealRepository.save(task.getAppeal());

    }

    @Override
    public void reject(long taskId, long employeeId, String text) {

        var task = taskRepository.findById(taskId).orElseThrow(()
                -> new ResourceNotFoundException(taskId));

        if(task.getEmployeeId() != employeeId)
            throw  new NotRightsException("");

        var report = new Report();
        report.setCreateDate(new Date());
        report.setReportStatus(ReportStatus.Rejected);
        report.setText(text);

        task.setOver(true);

        reportRepository.save(report);

        task.getAppeal().setStatusAppeal(StatusAppeal.Rejected);
        appealRepository.save(task.getAppeal());
    }

    @Override
    public List<ReportDto> getAll() {
        return reportRepository
                .findAll()
                .stream()
                .sorted(Comparator.comparing(Report::getCreateDate, Comparator.reverseOrder()))
                .map(ReportDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public ReportDto getById(long id) {
        var report = reportRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException(id));

        return new ReportDto(report);
    }

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
