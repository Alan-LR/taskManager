package com.example.taskManager.entities.taskUser;

public record TaskUserRequestDTO(
        Long taskId,
        Long userId,
        boolean responsible
) {
}
