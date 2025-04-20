package com.example.taskManager.taskUser;

import com.example.taskManager.tasks.Task;
import com.example.taskManager.tasks.TaskResponseDTO;
import com.example.taskManager.users.User;
import com.example.taskManager.users.UserResponseDTO;

public record TaskUserResponseDTO (
        TaskUserId id,
        UserResponseDTO user,
        TaskResponseDTO task
){
    public TaskUserResponseDTO(TaskUser data) {
        this(data.getId(), new UserResponseDTO(data.getUser()), new TaskResponseDTO(data.getTask()));
    }
}
