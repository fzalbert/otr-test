package org.example;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import org.springframework.stereotype.Component;

import java.util.logging.Logger;


@Component("appealsCreate")
public class AppealsCreateDelegate implements JavaDelegate {


    private final Logger LOGGER = Logger.getLogger(AppealsCreateDelegate.class.getName());

    public void execute(DelegateExecution execution) throws Exception {

        LOGGER.info("\n\n  ... LoggerDelegate invoked by "
                + "processDefinitionId=" + execution.getProcessDefinitionId()
                + ", activtyId=" + execution.getCurrentActivityId()
                + ", activtyName='" + execution.getCurrentActivityName() + "'"
                + ", processInstanceId=" + execution.getProcessInstanceId()
                + ", businessKey=" + execution.getProcessBusinessKey()
                + ", executionId=" + execution.getId()
                + " \n\n");
    }

}
