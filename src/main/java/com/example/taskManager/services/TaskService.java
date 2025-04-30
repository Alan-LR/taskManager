package com.example.taskManager.services;

import com.example.taskManager.constants.TasksMessages;
import com.example.taskManager.entities.tasks.StatusTask;
import com.example.taskManager.entities.tasks.Task;
import com.example.taskManager.dtos.tasks.TaskRequestDTO;
import com.example.taskManager.dtos.tasks.TaskResponseDTO;
import com.example.taskManager.entities.users.User;
import com.example.taskManager.repository.TaskRepository;
import com.example.taskManager.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private TaskRepository repository;
    private PermissionService permissionService;
    private UserService userService;

    public TaskService(TaskRepository repository,
                       UserRepository userRepository,
                       PermissionService permissionService,
                       UserService userService){
        this.repository = repository;
        this.permissionService = permissionService;
        this.userService = userService;
    }

    public TaskResponseDTO saveTask(TaskRequestDTO data, JwtAuthenticationToken token) {
        validateNameTask(data);
        User user = userService.getUserToken(token);

        if (!permissionService.isManagerOrAdmin(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, TasksMessages.PERMISSION_DENIED_DELEGATE_TASK);
        }

        Task task = new Task(data);
        task.setDateCreate(LocalDateTime.now());
        task.setStatus(StatusTask.OPEN);
        repository.save(task);
        return new TaskResponseDTO(task);
    }

    public void validateNameTask(TaskRequestDTO data) {
        var taskDb = repository.findByTitle(data.title());
        if (taskDb.isPresent()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public TaskResponseDTO getTask(Long id) {
        Optional<Task> taskOptional = repository.findById(id);
        Task task = taskOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, TasksMessages.TASK_NOT_FOUND));
        return new TaskResponseDTO(task);
    }

    public List<TaskResponseDTO> getAllTasks() {
        return repository.findAll().stream().map(TaskResponseDTO::new).toList();
    }

    public List<TaskResponseDTO> getTasksOpenClose(StatusTask status) {
        List<Task> openTasks = repository.findByStatus(status);
        return openTasks.stream().map(TaskResponseDTO::new).collect(Collectors.toList());
    }

    public List<TaskResponseDTO> getTaskByTitle(String title) {
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
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, TasksMessages.TASK_NOT_FOUND));

    }

    public TaskResponseDTO updateStatus(Long id, StatusTask status) {
        return repository.findById(id).map(
                existingTask -> {
                    existingTask.setStatus(status);

                    Task updateTask = repository.save(existingTask);
                    return new TaskResponseDTO(updateTask);
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, TasksMessages.TASK_NOT_FOUND));
    }
}
