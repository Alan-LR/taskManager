package com.example.taskManager.taskUser;

import com.example.taskManager.tasks.Task;
import com.example.taskManager.users.User;

public record TaskUserRequestDTO(
        Long taskId,
        Long userId,
        boolean responsible
) {
}
