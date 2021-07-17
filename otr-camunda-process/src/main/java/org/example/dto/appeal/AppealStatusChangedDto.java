package org.example.dto.appeal;

import lombok.*;
import org.example.dto.TaskStatus;
import org.example.dto.Theme;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppealStatusChangedDto {
    private Long id;
    private Long employeeId;
    private String nameOrg;
    private Theme theme;
    private String description;
    private Date createDate;
    private Integer statusAppeal;
    private String taskStatus;
}
