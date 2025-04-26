package com.example.taskManager.repository;

import com.example.taskManager.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByNameContainingIgnoreCase(String name);

    Optional<User> findByUserName(String userName);

}
