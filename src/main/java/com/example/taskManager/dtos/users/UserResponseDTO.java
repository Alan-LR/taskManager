package com.example.taskManager.dtos.users;

import com.example.taskManager.entities.users.User;

import java.time.LocalDateTime;

public record UserResponseDTO (
        long id,
        String name,
        String email,
        LocalDateTime registrationDate
){
    public UserResponseDTO(User user){
        this(user.getId(), user.getName(), user.getEmail(), user.getRegistrationDate());
    }
}
