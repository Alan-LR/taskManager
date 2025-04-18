package com.example.taskManager.tasks;

import com.example.taskManager.taskUser.TaskUser;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "tasks")
@Entity(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Task {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_seq")
    @SequenceGenerator(name = "task_seq", sequenceName = "task_seq", allocationSize = 1)
    private Long id;

    private String title;
    private String description;
    private LocalDateTime dateCreate;
    private LocalDateTime dateConclusion;

    // Para armazenar o nome do enum como string no banco
    @Enumerated(EnumType.STRING)
    private StatusTask status;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskUser> taskUsers = new ArrayList<>();

    public Task(TaskRequestDTO data) {
        this.title = data.title();
        this.description = data.description();
    }
}
