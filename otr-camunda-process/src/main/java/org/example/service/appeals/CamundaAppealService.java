package org.example.service.appeals;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.example.appeal.create.AppealActStatus;
import org.example.dto.appeal.Appeal;
import org.example.dto.appeal.AppealStatusChangedDto;
import org.springframework.beans.factory.annotation.Autowired;
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

        System.out.println("method changeStatus: Task status" + statusChangedDto.getTaskStatus());
        Appeal appeal = new Appeal(statusChangedDto);

        Task task = taskService
                .createTaskQuery()
                .processInstanceBusinessKey(appeal.getId().toString())
                .singleResult();

        System.out.println("method changeStatus: taskService entity Task: " + task);

        switch (statusChangedDto.getTaskStatus()){
            case "NEEDCHECK":
                System.out.println("method changeStatus: Enter to NEEDCHECK case: ");
                if(task.getTaskDefinitionKey().equals("change_appeal_key"))
                    this.update(appeal, task);
                break;
            case "NEEDUPDATE":
                System.out.println("method changeStatus: Enter to NEEDUPDATE case: ");
                if(task.getTaskDefinitionKey().equals("check_appeal_key")){
                    Map<String, Object> variables = appeal.toVariableMap();
                    variables.put("status", AppealActStatus.Change);
                    taskService.complete(task.getId(), variables);
                }
                break;
            case "NEEDREJECT":
                System.out.println("method changeStatus: Enter to NEEDREJECT case: ");
                if(task.getTaskDefinitionKey().equals("check_appeal_key")) {
                    Map<String, Object> variables = new HashMap<>();
                    variables.put("status", AppealActStatus.Denied);
                    taskService.complete(task.getId(), variables);
                }
                break;
            case "NEEDSUCCESS":
                System.out.println("method changeStatus: Enter to NEEDSUCCESS case: ");
                if(task.getTaskDefinitionKey().equals("check_appeal_key")) {
                    Map<String, Object> variables = new HashMap<>();
                    variables.put("status", AppealActStatus.Allow);
                    taskService.complete(task.getId(), variables);
                }
                break;
        }
    }

    @Override
    public void appoint(String login, Long appealId) {
        Task task = taskService
                .createTaskQuery()
                .processInstanceBusinessKey(appealId.toString())
                .singleResult();


        if(task == null)
            return;

        taskService.setAssignee(task.getId(), login);
    }

    private void update(Appeal appeal, Task task) {
        taskService.complete(task.getId(), appeal.toVariableMap());
    }

}
