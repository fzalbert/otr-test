package org.example.service.report;

import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.example.appeal.create.AppealActStatus;
import org.example.dto.report.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class CamundaReportService implements ReportService{


    @Autowired
    private TaskService taskService;


    @Override
    public void create(Report report) {
        System.out.println("report created: " + report.getId());

        Task task = taskService
                .createTaskQuery()
                .processInstanceBusinessKey(report.getAppealId().toString())
                .singleResult();

        if(task == null)
            return;


        Map<String, Object> variables = new HashMap<>();
        variables.put("report_id", report.getId());
        variables.put("report_text", report.getText());
        variables.put("report_date", report.getCreateDate());
        variables.put("status", AppealActStatus.getStatus(report.getReportStatus()));

        taskService
                .complete(task.getId(), variables);
    }
}
