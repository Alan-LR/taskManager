package com.example.taskManager.repository;

import com.example.taskManager.taskUser.TaskUser;
import com.example.taskManager.taskUser.TaskUserId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskUserRepository extends JpaRepository<TaskUser, TaskUserId> {

    List<TaskUser> findByUserId(Long id);

    //buscar no DB com nativeQuery - poderia ser feito com findByTaskId
    @Query(value = "SELECT * FROM task_user WHERE task_id = :taskId", nativeQuery = true)
    List<TaskUser> findByTaskIdNativeQuery(@Param("taskId") Long taskId);

    //pode ser excluido assim, ou apagar a linha da tabela task_user que é mais recomendado
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM task_user WHERE task_id = :taskId and user_id = :userId", nativeQuery = true)
    void removeUserFromTask(@Param("taskId") Long taskId, @Param("userId") Long userId);
}
