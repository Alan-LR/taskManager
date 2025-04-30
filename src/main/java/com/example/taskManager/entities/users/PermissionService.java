package com.example.taskManager.entities.users;

import com.example.taskManager.entities.role.Role;
import org.springframework.stereotype.Service;

@Service
public class PermissionService {

    public boolean createAdmin(User user){
        return user.getRoles().stream().anyMatch(r ->
                r.getName().equalsIgnoreCase(Role.Values.ADMIN.name()));
    }

    public Boolean isManagerOrAdmin(User user){
        return user.getRoles().stream()
                .anyMatch(role -> role.getName().equals(Role.Values.MANAGER.name()) || role.getName().equals(Role.Values.ADMIN.name()));
    }

}
