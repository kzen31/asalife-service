package com.asaproject.asalife.repositories;

import com.asaproject.asalife.domains.entities.Bobot;
import com.asaproject.asalife.domains.entities.Pertanyaan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BobotRepository extends JpaRepository<Bobot, Long> {
    List<Bobot> findAllByPertanyaan_Id(Long id);

    List<Bobot> findAllByPertanyaan(Pertanyaan pertanyaan);

    @Query(value = "SELECT * FROM Bobot c WHERE c.id = :id", nativeQuery = true)
    Bobot findBobotByIdNative(@Param("id") Long id);
}
