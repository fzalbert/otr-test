package org.example;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.task.Task;
import org.example.appeal.create.AppealActStatus;

import java.util.List;

public class TestEndedListener implements ExecutionListener {

    @Override
    public void notify(DelegateExecution execution) throws Exception {

        List<Task> tasks = execution
                .getProcessEngineServices()
                .getTaskService().createTaskQuery()
                .taskAssignee("demo")
                .processVariableValueEquals("orderId", "0815")
                .orderByDueDate().asc()
                .list();

        boolean changed = (boolean) execution.getVariable("changed");
        boolean denied = (boolean) execution.getVariable("denied");
        boolean approved = (boolean) execution.getVariable("approved");


        execution.setVariable("status", AppealActStatus.getStatus(approved, denied, changed));
    }
}
