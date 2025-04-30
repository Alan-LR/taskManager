package com.example.taskManager.dtos.tasksusers;

import com.example.taskManager.dtos.tasks.TaskResponseDTO;
import com.example.taskManager.dtos.users.UserResponseDTO;
import com.example.taskManager.entities.taskUser.TaskUser;
import com.example.taskManager.entities.taskUser.TaskUserId;

public record TaskUserResponseDTO (
        TaskUserId id,
        UserResponseDTO user,
        TaskResponseDTO task
){
    public TaskUserResponseDTO(TaskUser data) {
        this(data.getId(), new UserResponseDTO(data.getUser()), new TaskResponseDTO(data.getTask()));
    }
}
