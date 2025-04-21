package com.example.taskManager.taskUser;

import com.example.taskManager.tasks.StatusTask;
import com.example.taskManager.tasks.Task;

import java.time.LocalDateTime;

public record UserTasksResponseDTO(Long id, String title, String descriptio, LocalDateTime dataCreate,
                                   StatusTask status, boolean responsible) {
    public UserTasksResponseDTO(TaskUser data) {
        this(data.getTask().getId(), data.getTask().getTitle(), data.getTask().getDescription(), data.getTask().getDateCreate(), data.getTask().getStatus(), data.isResponsible());
    }
}
