package com.asaproject.asalife.repositories;

import com.asaproject.asalife.domains.entities.TaskMaintenance;
import com.asaproject.asalife.domains.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskMaintenanceRepository extends JpaRepository<TaskMaintenance, Long> {
    @Query(value = "SELECT * FROM Taskmaintenance c WHERE c.id = :id", nativeQuery = true)
    TaskMaintenance findTaskMaintenanceByIdNative(@Param("id") Long id);

    List<TaskMaintenance> findAllByUserOrderByCreatedAtAsc(User user);

    List<TaskMaintenance> findAllByOrderByCreatedAtAsc();

}
