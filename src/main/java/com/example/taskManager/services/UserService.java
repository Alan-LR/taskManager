package com.example.taskManager.services;

import com.example.taskManager.entities.role.Role;
import com.example.taskManager.entities.users.*;
import com.example.taskManager.repository.RoleRepository;
import com.example.taskManager.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
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

    private BCryptPasswordEncoder passwordEncoder;

    private PermissionService permissionService;

    //A recomendação atual é utilizar injeção de dependencia com construtor, mas o @Autowired não está errado e ainda é utilizado
    public UserService(UserRepository repository,
                       RoleRepository roleRepository,
                       BCryptPasswordEncoder passwordEncoder,
                       PermissionService permissionService
                       ) {
        this.repository = repository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.permissionService = permissionService;
    }

    private static final String USER_NOT_FOUND = "Usuário não encontrado";

    @Transactional
    public UserResponseDTO saveUser(UserRequestDTO data, JwtAuthenticationToken token, UserType typeUser){
        validateEmail(data);
        validatePermissionToCreate(typeUser, token);

        return new UserResponseDTO(repository.save(createUser(data, typeUser)));
    }

    public void validatePermissionToCreate(UserType typeUser, JwtAuthenticationToken token) {
        User user = repository.findById(Long.parseLong(token.getName()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, USER_NOT_FOUND));

        boolean hasPermission = switch (typeUser) {
            case ADMIN -> permissionService.createAdmin(user);
            case MANAGER, BASIC -> permissionService.isManagerOrAdmin(user);
        };

        if (!hasPermission) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Usuário sem permissão para criar " + typeUser.name());
        }
    }

    public void validateEmail(UserRequestDTO data) {
        var userDb = repository.findByEmail(data.email());
        if (userDb.isPresent()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public User createUser(UserRequestDTO data, UserType typeUser) {
        User user = new User(data);

        Role.Values roleValue = switch (typeUser) {
            case BASIC -> Role.Values.BASIC;
            case MANAGER -> Role.Values.MANAGER;
            case ADMIN -> Role.Values.ADMIN;
        };

        Role role = roleRepository.findByName(roleValue.name());
        user.setRoles(Set.of(role));
        user.setPassword(passwordEncoder.encode(data.password()));
        return user;
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

    public UserResponseDTO updateUser(Long id, UserRequestDTO data) {
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
