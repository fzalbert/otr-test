package com.example.employeesservice.dto.request;

import com.example.employeesservice.domain.enums.RoleType;
import lombok.Data;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

/**
 * Модель для обновления сотрудника
 */
@Data
public class UpdateEmployeeDto {

    @Nullable
    @Size(min = 2, message = "Длина имени минимум 2 символа")
    private String firstName;

    @Nullable
    @Size(min = 2, message = "Длина фамилии минимум 2 символа")
    private String lastName;

    @Nullable
    @Email(message = "Email должен быть корректным адресом электронной почты")
    private String email;

    @Nullable
    private RoleType roleType;
}
