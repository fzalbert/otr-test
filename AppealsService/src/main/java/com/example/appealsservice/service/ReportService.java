package com.example.appealsservice.service;

import com.example.appealsservice.domain.ReportStatus;
import com.example.appealsservice.dto.response.ReportDto;
import com.example.appealsservice.dto.response.TaskDto;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface ReportService {

    void approveOrReject(Long taskId, Long employeeId, Boolean isApprove,  String text) throws JsonProcessingException;

    List<ReportDto> getAll();

    ReportDto getById(Long id);

    List<ReportDto> getByStatus(ReportStatus status);

    List<ReportDto> getByFilter();
}
