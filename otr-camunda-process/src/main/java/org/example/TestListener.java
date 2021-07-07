package org.example;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.task.Task;

import java.util.List;
import java.util.logging.Logger;

public class TestListener implements TaskListener {

    private final Logger LOGGER = Logger.getLogger(LoggerDelegate.class.getName());


    @Override
    public void notify(DelegateTask delegateTask) {

        if(delegateTask.getEventName().equals(TaskListener.EVENTNAME_ASSIGNMENT)){

            List<Task> tasks = delegateTask
                    .getProcessEngineServices()
                    .getTaskService().createTaskQuery()
                    .taskId(delegateTask.getId())
                    .orderByDueDate().asc()
                    .list();

            String userId = tasks.get(0).getAssignee();

            if(userId == null || userId.isEmpty())
                return;

            List<User> test = delegateTask
                    .getProcessEngineServices()
                    .getIdentityService()
                    .createUserQuery()
                    .userId(userId)
                    .list();

            if(test.size() > 0)
                LOGGER.info("\n\n  ... TestListener invoked by "
                        + "userId" + test.get(0).getId()
                        + " \n\n");
        }
    }
}
