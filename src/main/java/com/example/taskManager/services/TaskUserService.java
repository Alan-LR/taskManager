package com.example.taskManager.services;

import com.example.taskManager.constants.TasksUsersMessages;
import com.example.taskManager.constants.UsersMessages;
import com.example.taskManager.dtos.tasksusers.TaskUserRequestDTO;
import com.example.taskManager.dtos.tasksusers.TaskUserResponseDTO;
import com.example.taskManager.dtos.tasksusers.UserTasksResponseDTO;
import com.example.taskManager.dtos.tasksusers.UsersOfTaskResponseDTO;
import com.example.taskManager.entities.taskUser.*;
import com.example.taskManager.repository.TaskRepository;
import com.example.taskManager.repository.TaskUserRepository;
import com.example.taskManager.repository.UserRepository;
import com.example.taskManager.entities.tasks.StatusTask;
import com.example.taskManager.entities.tasks.Task;
import com.example.taskManager.entities.users.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskUserService {

    private TaskUserRepository repository;
    private TaskRepository taskRepository;
    private UserRepository userRepository;
    private PermissionService permissionService;
    private UserService userService;

    public TaskUserService(TaskUserRepository repository,
                           TaskRepository taskRepository,
                           UserRepository userRepository,
                           PermissionService permissionService,
                           UserService userService){
        this.repository = repository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.permissionService = permissionService;
        this.userService = userService;
    }

    public TaskUserResponseDTO createTaskUser(TaskUserRequestDTO data, JwtAuthenticationToken token) {
        User userLogged = userService.getUserToken(token);

        if (!permissionService.isManagerOrAdmin(userLogged)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, TasksUsersMessages.PERMISSION_DENIED_DELEGATE_TASK);
        }

        User user = userRepository.findById(data.userId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, TasksUsersMessages.USER_NOT_FOUND));

        Task task = taskRepository.findById(data.taskId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, TasksUsersMessages.TASK_NOT_FOUND));

        if(task.getStatus().equals(StatusTask.CLOSE)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, TasksUsersMessages.TASK_CLOSED);
        }

        TaskUserId taskUserId = new TaskUserId(data.taskId(), data.userId());
        TaskUser taskUser = new TaskUser(taskUserId, task, user, data.responsible());
        repository.save(taskUser);
        return new TaskUserResponseDTO(taskUser);
    }

    public List<TaskUserResponseDTO> getAllTasksUsers(){
        return repository.findAll().stream().map(TaskUserResponseDTO::new).toList();
    }

    public List<UserTasksResponseDTO> getTasksOfUserByIdUser(Long id, JwtAuthenticationToken token){
        //User user = userRepository.findById(Long.parseLong(token.getName()))
        //        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, TasksUsersMessages.USER_NOT_FOUND));

        List<TaskUser> taskUsers = repository.findByUserId(id);
        if(taskUsers.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, TasksUsersMessages.USER_NOT_HAVE_TASKS);
        }
        return taskUsers.stream().map(UserTasksResponseDTO::new).collect(Collectors.toList());
    }

    public List<UsersOfTaskResponseDTO> getUsersOfTaskByIdTask(Long id){
        List<TaskUser> usersTask = repository.findByTaskIdNativeQuery(id);
        if(usersTask.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, TasksUsersMessages.TASK_NOT_HAVE_USERS);
        }
        return usersTask.stream().map(UsersOfTaskResponseDTO::new).collect(Collectors.toList());
    }

    public boolean deleteTaskUser(Long taskId, Long userId){
        if(taskRepository.existsById(taskId) && userRepository.existsById(userId)){
            repository.removeUserFromTask(taskId, userId);
            return true;
        }
        return false;
    }
}
