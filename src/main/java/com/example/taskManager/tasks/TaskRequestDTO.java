package com.example.taskManager.tasks;

public record TaskRequestDTO (String title, String description){
    public TaskRequestDTO(Task task){
        this(task.getTitle(), task.getDescription());
    }
}
