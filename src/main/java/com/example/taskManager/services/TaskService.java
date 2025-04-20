package com.example.taskManager.services;

import com.example.taskManager.repository.TaskRepository;
import com.example.taskManager.tasks.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository repository;
    private static final String TASK_NOT_FOUND = "Tarefa não encontrada";

    public TaskResponseDTO saveTask(TaskRequestDTO data) {
        Task task = new Task(data);
        task.setDateCreate(LocalDateTime.now());
        task.setStatus(StatusTask.OPEN);
        repository.save(task);
        return new TaskResponseDTO(task);
    }

    public TaskResponseDTO getTask(Long id) {
        Optional<Task> taskOptional = repository.findById(id);
        Task task = taskOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, TASK_NOT_FOUND));
        return new TaskResponseDTO(task);
    }

    public List<TaskResponseDTO> getAllTasks() {
        return repository.findAll().stream().map(TaskResponseDTO::new).toList();
    }

    public List<TaskResponseDTO> getTasksOpenClose(StatusTask status) {
        List<Task> openTasks = repository.findByStatus(status);
        return openTasks.stream().map(TaskResponseDTO::new).collect(Collectors.toList());
    }

    public List<TaskResponseDTO> getTaskByTitle(String title){
        List<Task> tasks = repository.findByTitleContainingIgnoreCase(title);
        return tasks.stream().map(TaskResponseDTO::new).collect(Collectors.toList());
    }

    public boolean deleteTask(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public TaskResponseDTO updateTask(Long id, TaskRequestDTO data) {
        return repository.findById(id)
                .map(existingTask -> {
                    existingTask.setTitle(data.title());
                    existingTask.setDescription(data.description());

                    Task updateTask = repository.save(existingTask);
                    return new TaskResponseDTO(updateTask);
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, TASK_NOT_FOUND));

    }


    public TaskResponseDTO updateStatus(Long id, StatusTask status) {
        return repository.findById(id).map(
                existingTask -> {
                    existingTask.setStatus(status);

                    Task updateTask = repository.save(existingTask);
                    return new TaskResponseDTO(updateTask);
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, TASK_NOT_FOUND));
    }
}
