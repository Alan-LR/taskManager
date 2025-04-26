package com.example.taskManager.entities.users;

public record UserResquestDTO (String name, String email, String pass){
    public UserResquestDTO(User user){
        this(user.getName(), user.getEmail(), user.getPass());
    }
}
