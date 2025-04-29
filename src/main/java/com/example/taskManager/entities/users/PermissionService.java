package com.example.taskManager.entities.users;

import com.example.taskManager.entities.role.Role;
import org.springframework.stereotype.Service;

@Service
public class PermissionService {

    public boolean createAdmin(User user){
        return user.getRoles().stream().anyMatch(r ->
                r.getName().equalsIgnoreCase(Role.Values.ADMIN.name()));
    }

    public boolean createManager(User user) {
        return user.getRoles().stream().anyMatch(r ->
                        r.getName().equalsIgnoreCase(Role.Values.MANAGER.name()) ||
                        r.getName().equalsIgnoreCase(Role.Values.ADMIN.name()));
    }

    public boolean createBasic(User user){
        return user.getRoles().stream().anyMatch(r ->
                r.getName().equalsIgnoreCase(Role.Values.MANAGER.name()) ||
                        r.getName().equalsIgnoreCase(Role.Values.ADMIN.name()));
    }
}
