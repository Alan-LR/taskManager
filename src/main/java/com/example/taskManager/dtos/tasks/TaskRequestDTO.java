package com.example.taskManager.dtos.tasks;

import com.example.taskManager.entities.tasks.Task;
import jakarta.validation.constraints.NotBlank;

public record TaskRequestDTO (
        @NotBlank(message = "O nome é obrigatório")
        String title,
        String description){
    public TaskRequestDTO(Task task){
        this(task.getTitle(), task.getDescription());
    }
}
