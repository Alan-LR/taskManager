package com.example.taskManager.controller;

import com.example.taskManager.services.TaskUserService;
import com.example.taskManager.taskUser.TaskUserRequestDTO;
import com.example.taskManager.taskUser.TaskUserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
