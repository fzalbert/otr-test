package org.example;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;


@Component("appealsCreate")
public class AppealsCreateDelegate implements JavaDelegate {

    private final Logger LOGGER = Logger.getLogger(LoggerDelegate.class.getName());

    public void execute(DelegateExecution execution) throws Exception {

        LOGGER.info("\n\n  ... LoggerDelegate invoked by "
                + "processDefinitionId=" + execution.getProcessDefinitionId()
                + ", activtyId=" + execution.getCurrentActivityId()
                + ", activtyName='" + execution.getCurrentActivityName() + "'"
                + ", processInstanceId=" + execution.getProcessInstanceId()
                + ", businessKey=" + execution.getProcessBusinessKey()
                + ", executionId=" + execution.getId()
                + " \n\n");


        execution.setVariable("appeals_id", "oaoioij ijsodfijoiasdjfo jjoiasjdfoijijaosdi");
        execution.setVariable("appeal_client_name", "user@gmail.com");
        execution.setVariable("appeal_status", "test_status");
        execution.setVariable("created_at", DateTime.now().toDateTimeISO().toString());
        execution.setVariable("appeal_theme", "Test theme");

    }

}
