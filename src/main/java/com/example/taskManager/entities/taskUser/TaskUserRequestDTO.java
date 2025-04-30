package com.example.taskManager.entities.taskUser;

import jakarta.validation.constraints.NotBlank;

public record TaskUserRequestDTO(
        @NotBlank(message = "A tarefa é obrigatória")
        Long taskId,
        @NotBlank(message = "O usuário é obrigatório")
        Long userId,
        boolean responsible
) {
}
