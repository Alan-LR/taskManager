package com.example.taskManager.dtos.tasks;

import com.example.taskManager.entities.tasks.StatusTask;
import com.example.taskManager.entities.tasks.Task;

import java.time.LocalDateTime;

public record TaskResponseDTO (
        long id,
        String title,
        String description,
        LocalDateTime dateCreate,
        StatusTask status
){
    public TaskResponseDTO(Task task){
        this(task.getId(), task.getTitle(), task.getDescription(), task.getDateCreate(), task.getStatus());
    }

}
