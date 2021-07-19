package com.example.appealsservice.service;

import com.example.appealsservice.domain.enums.ReportStatus;
import com.example.appealsservice.dto.response.ReportDto;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface ReportService {

    void approveOrReject(Long appealId, Long employeeId, Boolean isApprove,  String text) throws JsonProcessingException;

    List<ReportDto> getAll();

    ReportDto getById(Long id);

    List<ReportDto> getByStatus(ReportStatus status);
}
