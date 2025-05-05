package com.example.taskManager.dtos.comments;

import com.example.taskManager.dtos.tasks.TaskResponseDTO;
import com.example.taskManager.dtos.users.UserResponseDTO;
import com.example.taskManager.entities.comment.Comment;

import java.time.LocalDateTime;

public record CommentResponseDTO(
        String content,
        LocalDateTime registrationDate,
        UserResponseDTO user,
        TaskResponseDTO task
) {
    public CommentResponseDTO(Comment data) {
        this(data.getContent(), data.getRegistrationDate(), new UserResponseDTO(data.getUser()), new TaskResponseDTO(data.getTask())
        );
    }
}
