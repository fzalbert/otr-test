package com.example.appealsservice.httpModel;

public interface CheckUser {
    Boolean isClient(UserModel model);
    Boolean isEmployee(UserModel model);
    Boolean isAdmin(UserModel model);
}
