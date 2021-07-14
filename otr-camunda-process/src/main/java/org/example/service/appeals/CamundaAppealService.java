package org.example.service.appeals;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.example.dto.appeal.Appeal;
import org.example.dto.appeal.AppealStatusChangedDto;
import org.example.dto.appeal.StatusAppealParser;
import org.example.dto.user.Employee;
import org.example.service.BaseCamundaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class CamundaAppealService implements AppealService {

    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;

    @Override
    public void create(Appeal appeal) {
        System.out.println("appeal: " + appeal.getId());

        runtimeService
                .startProcessInstanceByKey(
                        "otr-camunda-process",
                        appeal.getId().toString(),
                        appeal.toVariableMap()
                );

    }

    @Override
    public void update(Appeal appeal) {

    }

    @Override
    public void changeStatus(AppealStatusChangedDto statusChangedDto) {

        Task task = taskService
                .createTaskQuery()
                .processInstanceBusinessKey(statusChangedDto.getAppeal().getId().toString())
                .singleResult();

        if(task.getTaskDefinitionKey().equals("check_appeal_key"))
            System.out.println("");
        else if(task.getTaskDefinitionKey().equals("change_appeal_key"))
            System.out.println("");

        switch (statusChangedDto.getTaskStatus()){
            case NEEDCHECK:
                if(task.getTaskDefinitionKey().equals("change_appeal_key"))
                    this.update(statusChangedDto.getAppeal(), task);
                break;
            case NEEDUPDATE:
                if(task.getTaskDefinitionKey().equals("check_appeal_key")){
                    Map<String, Object> variables = new HashMap<>();
                    taskService.complete(task.getId(), variables);
                }
                break;
            case NEEDREJECT:
                break;
            case NEEDSUCCESS:
                break;
        }
    }

    @Override
    public void appoint(Employee employee, Long appealId) {
        Task task = taskService
                .createTaskQuery()
                .processInstanceBusinessKey(appealId.toString())
                .singleResult();


        if(task == null)
            return;

        taskService
                .setAssignee(task.getId(), employee.getEmail());
    }

    private void update(Appeal appeal, Task task) {
        taskService
                .complete(task.getId(), appeal.toVariableMap());
    }

}
