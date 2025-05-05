package com.example.taskManager.services;

import com.example.taskManager.constants.CommentMessages;
import com.example.taskManager.constants.TasksMessages;
import com.example.taskManager.constants.TasksUsersMessages;
import com.example.taskManager.dtos.comments.CommentRequestDTO;
import com.example.taskManager.dtos.comments.CommentResponseDTO;
import com.example.taskManager.entities.comment.Comment;
import com.example.taskManager.entities.taskUser.TaskUser;
import com.example.taskManager.entities.tasks.StatusTask;
import com.example.taskManager.entities.tasks.Task;
import com.example.taskManager.entities.users.User;
import com.example.taskManager.repository.CommentRepository;
import com.example.taskManager.repository.TaskRepository;
import com.example.taskManager.repository.TaskUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CommentService {

    private CommentRepository repository;
    private PermissionService permissionService;
    private UserService userService;
    private TaskRepository taskRepository;
    private TaskUserRepository taskUserRepository;

    public CommentService(CommentRepository repository,
                          PermissionService permissionService,
                          UserService userService,
                          TaskRepository taskRepository,
                          TaskUserRepository taskUserRepository){
        this.repository = repository;
        this.permissionService = permissionService;
        this.userService = userService;
        this.taskRepository = taskRepository;
        this.taskUserRepository = taskUserRepository;
    }

    public CommentResponseDTO saveComment(CommentRequestDTO data, JwtAuthenticationToken token){
        User user = userService.getUserToken(token);

        Task task = taskRepository.findById(data.taskId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, TasksUsersMessages.TASK_NOT_FOUND));

        boolean isAdimin = permissionService.isManagerOrAdmin(user);

        if (!userPartOfTask(user.getId(), task.getId()) && !isAdimin) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, CommentMessages.USER_NOT_PART);
        }

        if(task.getStatus().equals(StatusTask.CLOSE)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, TasksUsersMessages.TASK_CLOSED);
        }

        Comment comment = new Comment();
        comment.setUser(user);
        comment.setTask(task);
        comment.setContent(data.content());

        repository.save(comment);
        return new CommentResponseDTO(comment);
    }

    public boolean userPartOfTask(Long userId, Long taskId){
        List<TaskUser> taskUser = taskUserRepository.findByTaskIdNativeQuery(taskId);
        return taskUser.stream().anyMatch(task -> task.getUser().getId().equals(userId));
    }

}
