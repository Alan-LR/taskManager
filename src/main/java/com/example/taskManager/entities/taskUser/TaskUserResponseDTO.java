package com.example.taskManager.entities.taskUser;

import com.example.taskManager.entities.tasks.TaskResponseDTO;
import com.example.taskManager.entities.users.UserResponseDTO;

public record TaskUserResponseDTO (
        TaskUserId id,
        UserResponseDTO user,
        TaskResponseDTO task
){
    public TaskUserResponseDTO(TaskUser data) {
        this(data.getId(), new UserResponseDTO(data.getUser()), new TaskResponseDTO(data.getTask()));
    }
}
