package com.example.appealsservice.service;

import com.example.appealsservice.domain.ReportStatus;
import com.example.appealsservice.dto.response.ReportDto;

import java.util.List;

public interface ReportService {

    void approve(long taskId, long employeeId, String text);
    void reject(long taskId, long employeeId, String text);

    List<ReportDto> getAll();

    ReportDto getById(long id);

    List<ReportDto> getByStatus(ReportStatus status);
}
