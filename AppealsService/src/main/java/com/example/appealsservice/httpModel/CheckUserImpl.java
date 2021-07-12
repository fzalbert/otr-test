package com.example.appealsservice.httpModel;

import org.springframework.stereotype.Service;

@Service
public class CheckUserImpl implements CheckUser {

    @Override
    public Boolean isClient(UserModel model){
        return model.getUserType().equals("ROLE_CLIENT");
    }

    @Override
    public Boolean isEmployee(UserModel model){
        return model.getUserType().equals("ROLE_EMPLOYEE") || model.getUserType().equals("ROLE_ADMIN") || model.getUserType().equals("ROLE_SUPER_ADMIN");
    }

    @Override
    public Boolean isAdmin(UserModel model){
        return model.getUserType().equals("ROLE_ADMIN") || model.getUserType().equals("ROLE_SUPER_ADMIN");
    }
}
