package org.example.service.appeals;

import org.camunda.bpm.engine.task.Task;
import org.example.dto.appeal.Appeal;
import org.example.dto.appeal.StatusAppealParser;
import org.example.dto.user.Employee;
import org.example.service.BaseCamundaService;
import org.springframework.stereotype.Service;


@Service
public class CamundaAppealService extends BaseCamundaService implements AppealService {

    @Override
    public void create(Appeal appeal) {
        System.out.println("appeal: " + appeal.getId());

        camunda.getRuntimeService()
                .startProcessInstanceByKey(appeal.getId().toString());

        camunda.getRuntimeService().createMessageCorrelation(appeal.getId().toString()) //
                .processInstanceBusinessKey(appeal.getId().toString())
                .setVariable("appeals_id", appeal.getId())
                .setVariable("appeal_client_name", appeal.getClientId())
                .setVariable("appeal_status", StatusAppealParser.toString(appeal.getStatusAppeal()))
                .setVariable("created_at", appeal.getCreateDate())
                .setVariable("appeal_theme", appeal.getTheme().getName())
                .setVariable("appeal_obj", appeal)
                .correlateWithResult();
    }

    @Override
    public void update(Appeal appeal) {

    }

    @Override
    public void appoint(Employee employee, Long appealId) {
        Task task = camunda.getTaskService()
                .createTaskQuery()
                .processInstanceBusinessKey(appealId.toString())
                .singleResult();


        if(task == null)
            return;

        camunda.getTaskService()
                .setAssignee(task.getId(), employee.getEmail());
    }

}
