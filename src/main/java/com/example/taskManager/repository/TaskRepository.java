package com.example.taskManager.repository;

import com.example.taskManager.tasks.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long>  {
}
