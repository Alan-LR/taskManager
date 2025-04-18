package com.example.taskManager.controller;

import com.example.taskManager.services.UserService;
import com.example.taskManager.users.UserResponseDTO;
import com.example.taskManager.users.UserResquestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping
    public ResponseEntity<UserResponseDTO> saveUser(@RequestBody UserResquestDTO data){
        UserResponseDTO savedUser = service.saveUser(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @RequestBody UserResquestDTO data){
        UserResponseDTO updateUser = service.updateUser(id, data);
        return  ResponseEntity.ok(updateUser);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>>getAllUsers(){
        List<UserResponseDTO> users = service.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable Long id){
        UserResponseDTO user = service.getUser(id);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        boolean deleted = service.deleteUser(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

}
