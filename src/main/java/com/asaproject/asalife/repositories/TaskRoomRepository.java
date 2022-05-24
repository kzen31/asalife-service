package com.asaproject.asalife.repositories;

import com.asaproject.asalife.domains.entities.*;
import com.asaproject.asalife.domains.models.interfaces.CountByMonth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRoomRepository extends JpaRepository<TaskRoom, Long> {
    List<TaskRoom> findByUserOrderByCreatedAtAsc(User user);

    List<TaskRoom> findAllByOrderByCreatedAtAsc();

    @Query(value = "SELECT * FROM Taskroom c WHERE c.id = :id", nativeQuery = true)
    TaskRoom findTaskRoomByIdNative(@Param("id") Long id);
}
