package org.example.service.report;

import org.camunda.bpm.engine.task.Task;
import org.example.appeal.create.AppealActStatus;
import org.example.dto.report.Report;
import org.example.service.BaseCamundaService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class CamundaReportService extends BaseCamundaService implements ReportService{

    @Override
    public void create(Report report) throws Exception {
        System.out.println("report created: " + report.getId());

        Task task = camunda.getTaskService()
                .createTaskQuery()
                .processInstanceBusinessKey(report.getAppeal().getId().toString())
                .singleResult();

        if(task == null)
            return;


        Map<String, Object> variables = new HashMap<>();
        variables.put("report_id", report.getId());
        variables.put("report_text", report.getText());
        variables.put("report_date", report.getCreateDate());
        variables.put("status", AppealActStatus.getStatus(report.getReportStatus()));

        camunda.getTaskService()
                .complete(task.getId(), variables);
    }
}
