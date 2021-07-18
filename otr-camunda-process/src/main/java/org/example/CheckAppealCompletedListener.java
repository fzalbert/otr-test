package org.example;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.task.Task;

import java.util.List;
import java.util.logging.Logger;

public class CheckAppealCompletedListener implements TaskListener {

    private final Logger LOGGER = Logger.getLogger(LoggerDelegate.class.getName());


    @Override
    public void notify(DelegateTask delegateTask) {

    }
}