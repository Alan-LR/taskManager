package com.example.taskManager.entities.tasks;

import jakarta.validation.constraints.NotBlank;

public record TaskRequestDTO (
        @NotBlank(message = "O nome é obrigatório")
        String title,
        String description){
    public TaskRequestDTO(Task task){
        this(task.getTitle(), task.getDescription());
    }
}
