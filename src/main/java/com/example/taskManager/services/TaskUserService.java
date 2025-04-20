package com.example.taskManager.services;

import com.example.taskManager.repository.TaskRepository;
import com.example.taskManager.repository.TaskUserRepository;
import com.example.taskManager.repository.UserRepository;
import com.example.taskManager.taskUser.TaskUser;
import com.example.taskManager.taskUser.TaskUserId;
import com.example.taskManager.taskUser.TaskUserRequestDTO;
import com.example.taskManager.taskUser.TaskUserResponseDTO;
import com.example.taskManager.tasks.Task;
import com.example.taskManager.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TaskUserService {

    @Autowired
    private TaskUserRepository repository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    public TaskUserResponseDTO createTaskUser(TaskUserRequestDTO data) {
        User user = userRepository.findById(data.userId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        Task task = taskRepository.findById(data.taskId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarefa não encontrada"));

        TaskUserId taskUserId = new TaskUserId(data.taskId(), data.userId());
        TaskUser taskUser = new TaskUser(taskUserId, task, user, data.responsible());
        repository.save(taskUser);
        return new TaskUserResponseDTO(taskUser);
    }
}
