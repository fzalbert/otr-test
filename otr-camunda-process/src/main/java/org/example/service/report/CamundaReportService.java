package org.example.service.report;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.task.Task;
import org.example.dto.report.Report;
import org.example.service.BaseCamundaService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CamundaReportService extends BaseCamundaService implements ReportService{

    @Override
    public void create(Report report) {
        System.out.println("report created: " + report.getId());

        Task task = camunda.getTaskService()
                .createTaskQuery()
                .processInstanceBusinessKey(report.getAppeal().getId().toString())
                .singleResult();

        if(task == null)
            return;


        camunda.getTaskService()
                .completeWithVariablesInReturn(task.getId());
    }
}
