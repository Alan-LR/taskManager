package com.example.taskManager.services;

import com.example.taskManager.repository.UserRepository;
import com.example.taskManager.users.User;
import com.example.taskManager.users.UserResponseDTO;
import com.example.taskManager.users.UserResquestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public UserResponseDTO saveUser(UserResquestDTO data){
        User user = new User(data);
        repository.save(user);
        return new UserResponseDTO(user);
    }

    public UserResponseDTO getUser(Long id){
        User user = repository.getReferenceById(id);
        return new UserResponseDTO(user);
    }

    public List<UserResponseDTO> getAllUsers(){
        return repository.findAll().stream().map(UserResponseDTO:: new).toList();
    }

    public boolean deleteUser(Long id){
        if(repository.existsById(id)){
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public UserResponseDTO updateUser(Long id, UserResquestDTO data) {
        return repository.findById(id)
                .map(existingUser -> {
                    existingUser.setName(data.name());
                    existingUser.setEmail(data.email());
                    existingUser.setPass(data.pass());

                    User updatedUser = repository.save(existingUser);
                    return new UserResponseDTO(updatedUser);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
    }
}
