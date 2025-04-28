package com.example.taskManager.entities.users;

public record UserResquestDTO (String name, String email, String password){
    public UserResquestDTO(User user){
        this(user.getName(), user.getEmail(), user.getPassword());
    }
}
