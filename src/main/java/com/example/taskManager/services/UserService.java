package com.example.taskManager.services;

import com.example.taskManager.entities.role.Role;
import com.example.taskManager.repository.RoleRepository;
import com.example.taskManager.repository.UserRepository;
import com.example.taskManager.entities.users.User;
import com.example.taskManager.entities.users.UserResponseDTO;
import com.example.taskManager.entities.users.UserResquestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserRepository repository;

    private RoleRepository roleRepository;

    //A recomendação atual é utilizar injeção de dependencia com construtor, mas o @Autowired não está errado e ainda é utilizado
    public UserService(UserRepository repository,
            RoleRepository roleRepository){
        this.repository = repository;
        this.roleRepository = roleRepository;
    }

    private static final String USER_NOT_FOUND = "Usuário não encontrado";

    public UserResponseDTO saveUser(UserResquestDTO data){
        Role basicRole = roleRepository.findByName(Role.Values.BASIC.name());

        var userDb = repository.findByEmail(data.email());
        if(userDb.isPresent()){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        User user = new User(data);
        user.setRoles(Set.of(basicRole));
        repository.save(user);
        return new UserResponseDTO(user);
    }

    public UserResponseDTO getUser(Long id){
        Optional<User> userOptional = repository.findById(id);
        User user = userOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, USER_NOT_FOUND));
        return new UserResponseDTO(user);
    }

    public List<UserResponseDTO> getAllUsers(){
        return repository.findAll().stream().map(UserResponseDTO:: new).toList();
    }

    public List<UserResponseDTO> getUserByName(String name){
        List<User> users = repository.findByNameContainingIgnoreCase(name);
        return users.stream().map(UserResponseDTO::new).collect(Collectors.toList());
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
                    existingUser.setPassword(data.password());

                    User updatedUser = repository.save(existingUser);
                    return new UserResponseDTO(updatedUser);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, USER_NOT_FOUND));
    }
}
