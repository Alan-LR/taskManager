package com.example.taskManager.entities.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRequestDTO(
        @NotBlank(message = "O nome é obrigatório")
        String name,
        @NotBlank(message = "O email é obrigatório")
        @Email(message = "O email deve ser válido")
        String email,
        @NotBlank(message = "A senha é obrigatória")
        String password) {
    public UserRequestDTO(User user){
        this(user.getName(), user.getEmail(), user.getPassword());
    }
}
