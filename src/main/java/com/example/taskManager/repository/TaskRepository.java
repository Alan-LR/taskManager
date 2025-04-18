package com.example.taskManager.repository;

import com.example.taskManager.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<User, Long>  {
}
