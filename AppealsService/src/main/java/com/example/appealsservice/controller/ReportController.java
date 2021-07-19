package com.example.appealsservice.controller;

import com.example.appealsservice.domain.enums.ReportStatus;
import com.example.appealsservice.dto.response.ReportDto;
import com.example.appealsservice.exception.NotRightsException;
import com.example.appealsservice.httpModel.CheckUser;
import com.example.appealsservice.service.ReportService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Log4j
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


    /**
     * Получить список отчетов
     */
    @GetMapping("list")
    public List<ReportDto> getAll() {
        return reportService.getAll();
    }

    /**
     * Получить отчет по id
     * @param id
     */
    @GetMapping("by-id")
    public ReportDto byId(@RequestParam Long id) {
        return reportService.getById(id);
    }

    /**
     * Создать отчет
     * @param appealId
     * @param isApprove
     * @param text
     */
    @GetMapping("approve-or-reject")
    public void approveOrReject(@RequestParam Long appealId, @RequestParam Boolean isApprove, @RequestParam String text) throws JsonProcessingException {

        log.debug("Request method reports/approve-or-reject for AppealId= " + appealId );

        if(checkUser.isClient(userModel))
            throw new NotRightsException("Not Rights");

        try {
            reportService.approveOrReject(appealId, userModel.getId(), isApprove, text);
            log.debug("SUCCESS Create report for AppealId : " + appealId);
        }
        catch (Exception e)
        {
            log.error("ERROR created report for AppealId : " + appealId);
        }
    }

    /**
     * Получить отчеты по статусу
     * @param status
     */
    @GetMapping("by-status")
    public List<ReportDto> getByStatus(ReportStatus status) {
        return reportService.getByStatus(status);
    }

}
