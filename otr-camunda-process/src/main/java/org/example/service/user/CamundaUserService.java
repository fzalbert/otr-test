package org.example.service.user;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.identity.User;
import org.example.dto.user.Employee;
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

    @Override
    public void update(Employee user) {
        User editedUser = identityService.createUserQuery()
                .userId(user.getLogin())
                .singleResult();

        if(editedUser == null){
            System.out.printf("Пользователь %s с логином не найден%n", user.getLogin());
            return;
        }

        editedUser.setEmail(user.getEmail());
        editedUser.setFirstName(user.getName());
        editedUser.setLastName(user.getLastName());
        editedUser.setPassword(user.getPassword());

        identityService
                .saveUser(editedUser);
    }
}
