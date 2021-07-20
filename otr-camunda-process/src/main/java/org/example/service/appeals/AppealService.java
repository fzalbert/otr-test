package org.example.service.appeals;

import org.example.dto.appeal.Appeal;
import org.example.dto.appeal.AppealStatusChangedDto;

public interface AppealService {

    void create(Appeal appeal);

    void update(Appeal appeal);

    void changeStatus(AppealStatusChangedDto statusChangedDto);

    void appoint(String login, Long appealId);
}
