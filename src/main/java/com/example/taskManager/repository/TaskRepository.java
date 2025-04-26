package com.example.taskManager.repository;

import com.example.taskManager.entities.tasks.StatusTask;
import com.example.taskManager.entities.tasks.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long>  {

    List<Task> findByStatus(StatusTask status);

    List<Task> findByTitleContainingIgnoreCase(String title);
}
