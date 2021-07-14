package org.example.service.user;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.spring.boot.starter.property.AdminUserProperty;
import org.example.dto.user.Employee;
import org.example.service.BaseCamundaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CamundaUserService implements UserService {


    @Autowired
    private IdentityService identityService;

    @Override
    public void create(Employee user) {

        User newUser = identityService.newUser(user.getLogin());
        newUser.setEmail(user.getEmail());
        newUser.setFirstName(user.getName());
        newUser.setLastName(user.getLastName());
        newUser.setPassword(user.getPassword());


        identityService
                .saveUser(newUser);
    }
}
