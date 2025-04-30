package com.example.taskManager.dtos.tasksusers;

import com.example.taskManager.entities.taskUser.TaskUser;
import com.example.taskManager.entities.tasks.StatusTask;

import java.time.LocalDateTime;

public record UserTasksResponseDTO(Long id, String title, String descriptio, LocalDateTime dataCreate,
                                   StatusTask status, boolean responsible) {
    public UserTasksResponseDTO(TaskUser data) {
        this(data.getTask().getId(), data.getTask().getTitle(), data.getTask().getDescription(), data.getTask().getDateCreate(), data.getTask().getStatus(), data.isResponsible());
    }
}
