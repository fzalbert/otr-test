package org.example.service.user;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.spring.boot.starter.property.AdminUserProperty;
import org.example.dto.user.Employee;
import org.example.service.BaseCamundaService;
import org.springframework.stereotype.Service;

@Service
public class CamundaUserService extends BaseCamundaService implements UserService {

    private ProcessEngine camunda;

    public CamundaUserService(){
        camunda = ProcessEngines.getDefaultProcessEngine();
    }

    @Override
    public void create(Employee user) {

        User camundaUser = new AdminUserProperty();
        camundaUser.setId(user.getEmail());
        camundaUser.setEmail(user.getEmail());
        camundaUser.setFirstName(user.getName());
        camundaUser.setLastName(user.getLastName());
        camundaUser.setPassword(user.getPassword());


        camunda.getIdentityService()
                .saveUser(camundaUser);
    }
}
