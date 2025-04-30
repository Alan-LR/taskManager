package com.example.taskManager.controller;

import com.example.taskManager.entities.users.UserType;
import com.example.taskManager.services.UserService;
import com.example.taskManager.dtos.users.UserResponseDTO;
import com.example.taskManager.dtos.users.UserRequestDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/basic")
    public ResponseEntity<UserResponseDTO> saveBasicUser(@RequestBody @Valid UserRequestDTO data, JwtAuthenticationToken token){
        UserResponseDTO savedUser = service.saveUser(data, token, UserType.BASIC);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @PostMapping("/manager")
    public ResponseEntity<UserResponseDTO> saveManagerUser(@RequestBody @Valid UserRequestDTO data, JwtAuthenticationToken token){
        UserResponseDTO savedUser = service.saveUser(data, token, UserType.MANAGER);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @PostMapping("/admin")
    public ResponseEntity<UserResponseDTO> saveAdminUser(@RequestBody @Valid UserRequestDTO data, JwtAuthenticationToken token){
        UserResponseDTO savedUser = service.saveUser(data, token, UserType.ADMIN);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @RequestBody @Valid UserRequestDTO data){
        UserResponseDTO updateUser = service.updateUser(id, data);
        return  ResponseEntity.ok(updateUser);
    }

    @GetMapping("/name")
    public ResponseEntity<List<UserResponseDTO>> getUserByName(@RequestParam String name){
        List<UserResponseDTO> users = service.getUserByName(name);
        return ResponseEntity.ok(users);
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
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
