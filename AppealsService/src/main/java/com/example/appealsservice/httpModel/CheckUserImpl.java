package com.example.appealsservice.httpModel;

import org.springframework.stereotype.Service;

@Service
public class CheckUserImpl implements CheckUser {

    @Override
    public Boolean isClient(UserModel model){
        return model.getUserType().equals("CLIENT");
    }

    @Override
    public Boolean isEmployee(UserModel model){
        return model.getUserType().equals("EMPLOYEE") || model.getUserType().equals("ADMIN") || model.getUserType().equals("SUPER_ADMIN");
    }

    @Override
    public Boolean isAdmin(UserModel model){
        return model.getUserType().equals("ADMIN") || model.getUserType().equals("SUPER_ADMIN");
    }
}
