package com.example.taskManager.repository;

import com.example.taskManager.taskUser.TaskUser;
import com.example.taskManager.taskUser.TaskUserId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskUserRepository extends JpaRepository<TaskUser, TaskUserId> {
}
