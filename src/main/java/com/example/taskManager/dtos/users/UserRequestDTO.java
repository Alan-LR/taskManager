package com.example.taskManager.dtos.users;

import com.example.taskManager.entities.users.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(
        @NotBlank(message = "O nome é obrigatório")
        String name,
        @NotBlank(message = "O email é obrigatório")
        @Email(message = "O email deve ser válido")
        String email,
        @NotBlank(message = "A senha é obrigatória")
        @Size(min = 6, message = "A senha deve ter no minimo 6 caracteres")
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*[!@#$%^&*(),.?\":{}|<>\\[\\]\\\\;']).*$",
                message = "A senha deve possuir uma letra maiuscula e um caractere especial")
        String password) {
    public UserRequestDTO(User user){
        this(user.getName(), user.getEmail(), user.getPassword());
    }
}
