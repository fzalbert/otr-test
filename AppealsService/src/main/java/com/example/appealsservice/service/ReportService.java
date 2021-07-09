package com.example.appealsservice.service;

import com.example.appealsservice.domain.ReportStatus;
import com.example.appealsservice.dto.response.ReportDto;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface ReportService {

    void approve(Long taskId, Long employeeId, String text) throws JsonProcessingException;
    void reject(Long taskId, Long employeeId, String text) throws JsonProcessingException;

    List<ReportDto> getAll();

    ReportDto getById(Long id);

    List<ReportDto> getByStatus(ReportStatus status);
}
