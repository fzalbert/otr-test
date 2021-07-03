package com.example.appealsservice.controller;

import com.example.appealsservice.domain.ReportStatus;
import com.example.appealsservice.dto.request.AppealRequestDto;
import com.example.appealsservice.dto.response.AppealDto;
import com.example.appealsservice.dto.response.ReportDto;
import com.example.appealsservice.service.impl.AppealServiceImpl;
import com.example.appealsservice.service.impl.ReportServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("reports")
public class ReportController {

    private final ReportServiceImpl reportServiceImpl;

    @Autowired
    public ReportController(ReportServiceImpl reportServiceImpl) {
        this.reportServiceImpl = reportServiceImpl;
    }

    @GetMapping()
    public List<ReportDto> getAll() {
        return reportServiceImpl.getAll();
    }

    @GetMapping("/{id}")
    public ReportDto byId(long id) {
        return reportServiceImpl.getById(id);
    }

    @GetMapping("/approve")
    public void approve(long taskId, long employeeId, String text) {
        reportServiceImpl.approve(taskId, employeeId, text);
    }

    @GetMapping("/reject")
    public void reject(long taskId, long employeeId, String text) {
        reportServiceImpl.approve(taskId, employeeId, text);
    }

    @GetMapping("byStatus")
    public List<ReportDto> getByStatus(ReportStatus status) {
        return reportServiceImpl.getByStatus(status);
    }

}
