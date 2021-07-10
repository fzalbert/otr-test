package com.example.appealsservice.controller;

import com.example.appealsservice.domain.ReportStatus;
import com.example.appealsservice.dto.request.AppealRequestDto;
import com.example.appealsservice.dto.response.AppealDto;
import com.example.appealsservice.dto.response.ReportDto;
import com.example.appealsservice.service.impl.AppealServiceImpl;
import com.example.appealsservice.service.impl.ReportServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("reports")
public class ReportController extends AuthorizeController {

    private final ReportServiceImpl reportServiceImpl;

    @Autowired
    public ReportController(ReportServiceImpl reportServiceImpl, HttpServletRequest request) {
        super(request);
        this.reportServiceImpl = reportServiceImpl;
    }

    @GetMapping()
    public List<ReportDto> getAll() {
        return reportServiceImpl.getAll();
    }

    @GetMapping("/{id}")
    public ReportDto byId(Long id) {
        return reportServiceImpl.getById(id);
    }

    @GetMapping("/approve")
    public void approve(long taskId, String text) throws JsonProcessingException {
        if(employeeModel == null)

        reportServiceImpl.approve(taskId, employeeModel.getId(), text);
    }

    @GetMapping("/reject")
    public void reject(long taskId, String text) throws JsonProcessingException {
        reportServiceImpl.approve(taskId, employeeModel.getId(), text);
    }

    @GetMapping("byStatus")
    public List<ReportDto> getByStatus(ReportStatus status) {
        return reportServiceImpl.getByStatus(status);
    }

}
