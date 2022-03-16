package com.asaproject.asalife.repositories;

import com.asaproject.asalife.domains.entities.Pertanyaan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PertanyaanRepository extends JpaRepository<Pertanyaan, Long> {
    @Query(value = "SELECT * FROM Pertanyaan c WHERE c.id = :id", nativeQuery = true)
    Pertanyaan findPertanyaanByIdNative(@Param("id") Long id);
}
