package com.asaproject.asalife.repositories;

import com.asaproject.asalife.domains.entities.Ruang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RuangRepository extends JpaRepository<Ruang, Long> {
    @Query(value = "SELECT * FROM Ruang c WHERE c.id = :id", nativeQuery = true)
    Ruang findRuangByIdNative(@Param("id") Long id);
}
