package org.example.dto.appeal;

import lombok.Getter;
import lombok.Setter;
import org.example.dto.TaskStatus;

@Getter
@Setter
public class AppealStatusChangedDto {
    private Appeal appeal;
    private TaskStatus taskStatus;

    public AppealStatusChangedDto(Appeal appeal, TaskStatus taskStatus) {
        this.appeal = appeal;
        this.taskStatus = taskStatus;
    }
}
