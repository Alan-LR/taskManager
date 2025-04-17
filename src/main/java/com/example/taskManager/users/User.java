package com.example.taskManager.users;

import com.example.taskManager.taskUser.TaskUser;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "users")
@Entity(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
    private Long id;

    private String name;
    private String email;
    private String pass;
    private LocalDateTime registrationDate;

    @PrePersist
    protected void onCreate() {
        registrationDate = LocalDateTime.now();
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskUser> taskUsers = new ArrayList<>();

    public User(UserResquestDTO data){
        this.name = data.name();
        this.email = data.email();
        this.pass = data.pass();
    }

}
