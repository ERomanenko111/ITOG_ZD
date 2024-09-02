package com.example.User_server.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Запрос на регистрацию")
public class SignUpRequest {
    @Schema(description = "Логин", example = "johndoe")
    @Size(min = 5, max = 50, message = "Логин должен содержать от 5 до 50 символов")
    @NotBlank(message = "Логин не может быть пустыми")
    private String login;

    @Schema(description = "Пароль", example = "password123")
    @Size(max = 255, message = "Длина пароля должна быть не более 255 символов")
    private String password;

    @Schema(description = "Имя", example = "Иван")
    @Size(min = 2, max = 255, message = "Имя должно содержать от 2 до 255 символов")
    @NotBlank(message = "Имя не может быть пустыми")
    private String firstname;

    @Schema(description = "Фамилия", example = "Иванов")
    @Size(min = 2, max = 255, message = "Фамилия должна содержать от 2 до 255 символов")
    @NotBlank(message = "Фамилия не может быть пустой")
    private String surname;

    @Schema(description = "Отчество", example = "Иванович")
    @Size(min = 2, max = 255, message = "Отчество должно содержать от 2 до 255 символов")
    private String lastname;
}

