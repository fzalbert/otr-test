package org.example.service;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;

public abstract class BaseCamundaService {

    protected final ProcessEngine camunda;

    public BaseCamundaService(){
        camunda = ProcessEngines.getDefaultProcessEngine();
    }

}
