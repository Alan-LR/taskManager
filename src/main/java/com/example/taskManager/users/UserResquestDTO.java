package com.example.taskManager.users;

public record UserResquestDTO (String name, String email, String pass){
    public UserResquestDTO(User user){
        this(user.getName(), user.getEmail(), user.getPass());
    }
}
