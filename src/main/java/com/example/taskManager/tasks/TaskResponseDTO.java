package com.example.taskManager.tasks;

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
