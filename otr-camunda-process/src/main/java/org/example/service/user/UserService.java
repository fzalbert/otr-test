package org.example.service.user;

import org.example.dto.user.Employee;

public interface UserService {

    void create(Employee user);

    void update(Employee user);
}
