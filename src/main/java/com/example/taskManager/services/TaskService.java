package com.example.taskManager.services;

import com.example.taskManager.entities.role.Role;
import com.example.taskManager.entities.tasks.StatusTask;
import com.example.taskManager.entities.tasks.Task;
import com.example.taskManager.entities.tasks.TaskRequestDTO;
import com.example.taskManager.entities.tasks.TaskResponseDTO;
import com.example.taskManager.entities.users.User;
import com.example.taskManager.entities.users.UserRequestDTO;
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
    private UserRepository userRepository;
    private static final String TASK_NOT_FOUND = "Tarefa não encontrada";

    public TaskService(TaskRepository repository,
                       UserRepository userRepository){
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public TaskResponseDTO saveTask(TaskRequestDTO data, JwtAuthenticationToken token) {
        validateNameTask(data);
        Long userId = Long.parseLong(token.getName());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        if (!isManagerOrAdmin(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Apenas usuários com permissão MANAGER ou ADMIN podem criar tasks");
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

    public Boolean isManagerOrAdmin(User user){
        return user.getRoles().stream()
                .anyMatch(role -> role.getName().equals(Role.Values.MANAGER.name()) || role.getName().equals(Role.Values.ADMIN.name()));
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
