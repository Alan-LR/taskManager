package com.example.taskManager.taskUser;

import java.time.LocalDateTime;

public record UsersOfTaskResponseDTO(Long id,
                                     String name,
                                     String email,
                                     LocalDateTime registrationDate,
                                     boolean responsible) {
    public UsersOfTaskResponseDTO(TaskUser data) {
        this(data.getUser().getId(), data.getUser().getName(), data.getUser().getEmail(), data.getUser().getRegistrationDate(), data.isResponsible());
    }
}
