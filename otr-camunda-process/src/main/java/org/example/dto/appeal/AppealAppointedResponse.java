package org.example.dto.appeal;

import lombok.Getter;
import lombok.Setter;
import org.example.dto.user.Employee;

@Getter
@Setter
public class AppealAppointedResponse {

    private Employee employee;
    private Long appealId;
}
