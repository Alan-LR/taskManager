package com.example.taskManager.services;

import com.example.taskManager.entities.taskUser.*;
import com.example.taskManager.repository.TaskRepository;
import com.example.taskManager.repository.TaskUserRepository;
import com.example.taskManager.repository.UserRepository;
import com.example.taskManager.entities.tasks.StatusTask;
import com.example.taskManager.entities.tasks.Task;
import com.example.taskManager.entities.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskUserService {

    @Autowired
    private TaskUserRepository repository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    private static final String TASK_CLOSED = "Não é possível adicionar usuários a uma tarefa com status 'CLOSE'.";
    private static final String USER_NOT_HAVE_TASKS = "Esse usuário não possuí tarefas";
    private static final String TASK_NOT_HAVE_USERS = "Essa tarefa não possuí usuários";

    public TaskUserResponseDTO createTaskUser(TaskUserRequestDTO data) {
        User user = userRepository.findById(data.userId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        Task task = taskRepository.findById(data.taskId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarefa não encontrada"));

        if(task.getStatus().equals(StatusTask.CLOSE)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, TASK_CLOSED);
        }

        TaskUserId taskUserId = new TaskUserId(data.taskId(), data.userId());
        TaskUser taskUser = new TaskUser(taskUserId, task, user, data.responsible());
        repository.save(taskUser);
        return new TaskUserResponseDTO(taskUser);
    }

    public List<TaskUserResponseDTO> getAllTasksUsers(){
        return repository.findAll().stream().map(TaskUserResponseDTO::new).toList();
    }

    public List<UserTasksResponseDTO> getTasksOfUserByIdUser(Long id){
        List<TaskUser> taskUsers = repository.findByUserId(id);
        if(taskUsers.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, USER_NOT_HAVE_TASKS);
        }
        return taskUsers.stream().map(UserTasksResponseDTO::new).collect(Collectors.toList());
    }

    public List<UsersOfTaskResponseDTO> getUsersOfTaskByIdTask(Long id){
        List<TaskUser> usersTask = repository.findByTaskIdNativeQuery(id);
        if(usersTask.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, TASK_NOT_HAVE_USERS);
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
