package com.example.taskManager.dtos.comments;

import com.example.taskManager.entities.comment.Comment;
import jakarta.validation.constraints.NotBlank;

public record CommentRequestDTO(
        @NotBlank(message = "Um comentário deve conter uma tarefa")
        Long taskId,
        @NotBlank(message = "Um comentário deve conter uma mensagem")
        String content
) {
}
