package org.example.dto.appeal;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.example.dto.user.Employee;

@Data
public class AppealAppointedResponse {

    private String login;
    private Long appealId;
}
