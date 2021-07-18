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
    @Size(min = 5, max = 20, message = "Длина логина минимум 5 символов, максимум 20")
    private String login;

    @Nullable
    @Size(min = 5, message = "Длина имени минимум 5 символов")
    private String firstName;

    @Nullable
    @Size(min = 5, message = "Длина пароля минимум 5 символов")
    private String password;

    @Nullable
    @Size(min = 5, message = "Длина фамилии минимум 5 символов")
    private String lastName;

    @Nullable
    @Email(message = "Email должен быть корректным адресом электронной почты")
    private String email;

    @Nullable
    private RoleType roleType;
}
