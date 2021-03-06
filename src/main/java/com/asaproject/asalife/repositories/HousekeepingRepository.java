package com.asaproject.asalife.repositories;

import com.asaproject.asalife.domains.entities.Housekeeping;
import com.asaproject.asalife.domains.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HousekeepingRepository extends JpaRepository<Housekeeping, Long> {
    List<Housekeeping> findAllByUserOrderByCreatedAtAsc(User user);

    @Query(value = "SELECT * FROM Housekeeping c WHERE c.id = :id", nativeQuery = true)
    Housekeeping findHousekeepingByIdNative(@Param("id") Long id);

    @Query(value = "SELECT * FROM Housekeeping c ORDER BY c.created_at ASC", nativeQuery = true)
    List<Housekeeping> findAllAndOrder();
}
