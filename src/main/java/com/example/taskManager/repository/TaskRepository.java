package com.example.taskManager.repository;

import com.example.taskManager.entities.tasks.StatusTask;
import com.example.taskManager.entities.tasks.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long>  {

    List<Task> findByStatus(StatusTask status);

    Optional<Task> findByTitle(String name);

    List<Task> findByTitleContainingIgnoreCase(String title);
}
