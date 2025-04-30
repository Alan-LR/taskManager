package com.example.taskManager.controller;

import com.example.taskManager.services.TaskUserService;
import com.example.taskManager.dtos.tasksusers.TaskUserRequestDTO;
import com.example.taskManager.dtos.tasksusers.TaskUserResponseDTO;
import com.example.taskManager.dtos.tasksusers.UserTasksResponseDTO;
import com.example.taskManager.dtos.tasksusers.UsersOfTaskResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/taskuser")
public class TaskUserController {

    @Autowired
    private TaskUserService service;

    @PostMapping()
    public ResponseEntity<TaskUserResponseDTO> createTaskUser(@RequestBody TaskUserRequestDTO data, JwtAuthenticationToken token){
        TaskUserResponseDTO responseDTO = service.createTaskUser(data, token);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<TaskUserResponseDTO>> getAllTasksUsers(){
        List<TaskUserResponseDTO> tasksUsers = service.getAllTasksUsers();
        return ResponseEntity.ok(tasksUsers);
    }

    @GetMapping("/idUser/{id}")
    public ResponseEntity<List<UserTasksResponseDTO>> getTasksOfUserByIdUser(@PathVariable Long id, JwtAuthenticationToken token){
        List<UserTasksResponseDTO> taskUserResponseDTOS = service.getTasksOfUserByIdUser(id, token);
        return ResponseEntity.ok(taskUserResponseDTOS);
    }

    @GetMapping("/idTask/{id}")
    public ResponseEntity<List<UsersOfTaskResponseDTO>> getUsersOfTaskByIdTask(@PathVariable Long id){
        List<UsersOfTaskResponseDTO> usersOfTaskResponseDTOS = service.getUsersOfTaskByIdTask(id);
        return ResponseEntity.ok(usersOfTaskResponseDTOS);
    }

    @DeleteMapping("/{taskId}/{userId}")
    public ResponseEntity<Void> deleteTaskUser(@PathVariable Long taskId, @PathVariable Long userId){
        boolean deleted = service.deleteTaskUser(taskId, userId);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
