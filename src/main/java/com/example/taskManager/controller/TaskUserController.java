package com.example.taskManager.controller;

import com.example.taskManager.services.TaskUserService;
import com.example.taskManager.taskUser.TaskUserRequestDTO;
import com.example.taskManager.taskUser.TaskUserResponseDTO;
import com.example.taskManager.taskUser.UserTasksResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/taskuser")
public class TaskUserController {

    @Autowired
    private TaskUserService service;

    @PostMapping()
    public ResponseEntity<TaskUserResponseDTO> createTaskUser(@RequestBody TaskUserRequestDTO data){
        TaskUserResponseDTO responseDTO = service.createTaskUser(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<TaskUserResponseDTO>> getAllTasksUsers(){
        List<TaskUserResponseDTO> tasksUsers = service.getAllTasksUsers();
        return ResponseEntity.ok(tasksUsers);
    }

    @GetMapping("/idUser/{id}")
    public ResponseEntity<List<UserTasksResponseDTO>> getTasksOfUserByIdUser(@PathVariable Long id){
        List<UserTasksResponseDTO> taskUserResponseDTOS = service.getTasksOfUserByIdUser(id);
        return ResponseEntity.ok(taskUserResponseDTOS);
    }
}
