package com.example.taskManager.entities.tasks;

public record TaskRequestDTO (String title, String description){
    public TaskRequestDTO(Task task){
        this(task.getTitle(), task.getDescription());
    }
}
