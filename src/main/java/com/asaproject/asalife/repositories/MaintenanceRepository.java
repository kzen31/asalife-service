package com.asaproject.asalife.repositories;

import com.asaproject.asalife.domains.entities.Maintenance;
import com.asaproject.asalife.domains.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {
    List<Maintenance> findAllByUserOrderByCreatedAtAsc(User user);

    List<Maintenance> findAllByPicNrpOrderByCreatedAtAsc(String picNrp);

    @Query(value = "SELECT * FROM Maintenance c WHERE c.id = :id", nativeQuery = true)
    Maintenance findMaintenanceByIdNative(@Param("id") Long id);

    @Query(value = "SELECT * FROM Maintenance c ORDER BY c.created_at ASC", nativeQuery = true)
    List<Maintenance> findAllAndOrder();
}
