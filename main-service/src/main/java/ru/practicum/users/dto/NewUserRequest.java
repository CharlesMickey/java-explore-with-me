package ru.practicum.users.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewUserRequest {

    @NotBlank(message = "User name не может быть пустым")
    @Size(min = 2, max = 250)
    private String name;

    @NotBlank(message = "Email не может быть пустым")
    @Email(message = "Не верный формат email")
    @Size(min = 6, max = 254)
    private String email;
}
