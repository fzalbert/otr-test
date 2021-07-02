package com.example.appealsservice.service.impl;

import com.example.appealsservice.domain.Report;
import com.example.appealsservice.domain.ReportStatus;
import com.example.appealsservice.exception.ResourceNotFoundException;
import com.example.appealsservice.repository.AppealRepository;
import com.example.appealsservice.repository.ReportRepository;
import com.example.appealsservice.repository.TaskRepository;
import com.example.appealsservice.repository.ThemeRepository;
import com.example.appealsservice.service.AppealService;
import com.example.appealsservice.service.ReportService;
import com.example.appealsservice.service.TaskService;

import java.util.Date;

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
    public void approve(long taskId, String text) {

        var task = taskRepository.findById(taskId).orElseThrow(()
                -> new ResourceNotFoundException(taskId));

        var report = new Report();
        report.setCreateDate(new Date());
        report.setReportStatus(ReportStatus.Success);
        report.setText(text);

        task.setOver(true);

        reportRepository.save(report);

    }

    @Override
    public void reject(long taskId, String text) {

    }
}
