package com.example.appealsservice.controller;

import com.example.appealsservice.domain.enums.ReportStatus;
import com.example.appealsservice.dto.response.ReportDto;
import com.example.appealsservice.exception.NotRightsException;
import com.example.appealsservice.httpModel.CheckUser;
import com.example.appealsservice.service.ReportService;
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
    private final CheckUser checkUser;

    @Autowired
    public ReportController(ReportService reportService, CheckUser checkUser, HttpServletRequest request) {
        super(request);

        this.reportService = reportService;
        this.checkUser = checkUser;
    }


    @GetMapping("list")
    public List<ReportDto> getAll() {
        return reportService.getAll();
    }

    @GetMapping("by-id")
    public ReportDto byId(@RequestParam Long id) {
        return reportService.getById(id);
    }

    @GetMapping("approve-or-reject")
    public void approveOrReject(@RequestParam Long taskId, @RequestParam Boolean isApprove, @RequestParam String text) throws JsonProcessingException {
        if(!checkUser.isEmployee(userModel))
            throw new NotRightsException("Not Rigthts");

        reportService.approveOrReject(taskId, userModel.getId(), isApprove, text);
    }

    @GetMapping("by-status")
    public List<ReportDto> getByStatus(ReportStatus status) {
        return reportService.getByStatus(status);
    }

}
