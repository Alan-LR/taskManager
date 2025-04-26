package com.example.taskManager.entities.role;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    private String name;

    @Getter
    public enum Values{
        ADMIN(1L),
        BASIC(2L);

        long roleId;

        Values(Long roleId){
            this.roleId = roleId;
        }
    }
}
