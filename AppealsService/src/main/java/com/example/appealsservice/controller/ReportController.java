package com.example.appealsservice.controller;

import com.example.appealsservice.domain.ReportStatus;
import com.example.appealsservice.dto.response.ReportDto;
import com.example.appealsservice.exception.NotRightsException;
import com.example.appealsservice.service.ReportService;
import com.example.appealsservice.service.impl.ReportServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Scope("prototype")
@RestController
@RequestMapping("reports")
public class ReportController extends AuthorizeController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService, HttpServletRequest request) {
        super(request);
        this.reportService = reportService;
    }

    @GetMapping()
    public List<ReportDto> getAll() {
        return reportService.getAll();
    }

    @GetMapping("/{id}")
    public ReportDto byId(Long id) {
        return reportService.getById(id);
    }

    @GetMapping("/approveOrReject")
    public void approveOrReject(Long taskId, Boolean isApprove, String text) throws JsonProcessingException {
        if(employeeModel == null)
            throw new NotRightsException("Not Rights");

        reportService.approveOrReject(taskId, employeeModel.getId(), isApprove, text);
    }

    @GetMapping("byStatus")
    public List<ReportDto> getByStatus(ReportStatus status) {
        return reportService.getByStatus(status);
    }

}
