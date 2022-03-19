package com.asaproject.asalife.repositories;

import com.asaproject.asalife.domains.entities.RuangDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RuangDetailRepository extends JpaRepository<RuangDetail, Long> {
    List<RuangDetail> findAllByRuang_Id(Long id);

    @Query(value = "SELECT * FROM RuangDetail c WHERE c.id = :id", nativeQuery = true)
    RuangDetail findRuangDetailByIdNative(@Param("id") Long id);
}
