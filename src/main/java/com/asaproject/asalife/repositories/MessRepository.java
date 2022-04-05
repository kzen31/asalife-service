package com.asaproject.asalife.repositories;

import com.asaproject.asalife.domains.entities.Catering;
import com.asaproject.asalife.domains.entities.Mess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessRepository extends JpaRepository<Mess, Long> {
    Mess findByNameIgnoreCaseAndDeletedAtIsNull(String name);

    @Query(value = "SELECT * FROM Mess c WHERE c.id = :id", nativeQuery = true)
    Mess findMessByIdNative(@Param("id") Long id);
}
