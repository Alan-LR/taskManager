package com.example.taskManager.taskUser;

import com.example.taskManager.tasks.Task;
import com.example.taskManager.users.User;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "task_user")
@Entity(name = "task_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class TaskUser {

    @EmbeddedId
    private TaskUserId id = new TaskUserId();

    @ManyToOne
    @MapsId("taskId")
    @JoinColumn(name = "task_id")
    private Task task;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    private boolean responsible;
}
