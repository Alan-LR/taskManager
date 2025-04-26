package com.example.taskManager.controller;

import com.example.taskManager.services.TaskService;
import com.example.taskManager.entities.tasks.StatusTask;
import com.example.taskManager.entities.tasks.TaskRequestDTO;
import com.example.taskManager.entities.tasks.TaskResponseDTO;
import com.example.taskManager.entities.tasks.UpdateTaskStatusRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService service;

    @PostMapping
    public ResponseEntity<TaskResponseDTO> saveTask(@RequestBody TaskRequestDTO data) {
        TaskResponseDTO savedTask = service.saveTask(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> updateTask(@PathVariable Long id, @RequestBody TaskRequestDTO data) {
        TaskResponseDTO updateTask = service.updateTask(id, data);
        return ResponseEntity.ok(updateTask);
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<TaskResponseDTO> updateStatus(@PathVariable Long id, @RequestBody UpdateTaskStatusRequestDTO status) {
        TaskResponseDTO updateTask = service.updateStatus(id, status.status());
        return ResponseEntity.ok(updateTask);
    }

    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> getAllTasks() {
        List<TaskResponseDTO> tasks = service.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/title")
    public ResponseEntity<List<TaskResponseDTO>> getTaskByTitle(@RequestParam String title){
        List<TaskResponseDTO> tasks = service.getTaskByTitle(title);
        if(tasks.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/open")
    public ResponseEntity<List<TaskResponseDTO>> getTasksOpen(){
        List<TaskResponseDTO> tasksOpen = service.getTasksOpenClose(StatusTask.OPEN);
        return ResponseEntity.ok(tasksOpen);
    }

    @GetMapping("/close")
    public ResponseEntity<List<TaskResponseDTO>> getTasksOpenClose(){
        List<TaskResponseDTO> tasksOpen = service.getTasksOpenClose(StatusTask.CLOSE);
        return ResponseEntity.ok(tasksOpen);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getTask(@PathVariable Long id) {
        TaskResponseDTO task = service.getTask(id);
        return ResponseEntity.ok(task);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        boolean deleted = service.deleteTask(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
