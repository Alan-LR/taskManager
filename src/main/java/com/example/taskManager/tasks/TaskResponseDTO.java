package com.example.taskManager.tasks;

public record TaskResponseDTO (
        long id,
        String title,
        String description,
        String dateCreate,
        StatusTask status
){
    public TaskResponseDTO(Task task){
        this(task.getId(), task.getTitle(), task.getDescription(), task.getDateCreate(), task.getStatus());
    }

}
