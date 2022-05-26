package com.asaproject.asalife.repositories;

import com.asaproject.asalife.domains.entities.TaskMess;
import com.asaproject.asalife.domains.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskMessRepository extends JpaRepository<TaskMess, Long> {
    List<TaskMess> findByUserOrderByCreatedAtAsc(User user);

    List<TaskMess> findAllByOrderByCreatedAtAsc();

    @Query(value = "SELECT * FROM Taskmess c WHERE c.id = :id", nativeQuery = true)
    TaskMess findTaskMessByIdNative(@Param("id") Long id);
}
