package org.example.service.appeals;

import org.example.dto.appeal.Appeal;
import org.example.dto.user.Employee;

public interface AppealService {

    void create(Appeal appeal);

    void update(Appeal appeal);

    void appoint(Employee employeeLogin, Long appealId);
}
