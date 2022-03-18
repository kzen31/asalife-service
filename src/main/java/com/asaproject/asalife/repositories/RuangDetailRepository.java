package com.asaproject.asalife.repositories;

import com.asaproject.asalife.domains.entities.RuangDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RuangDetailRepository extends JpaRepository<RuangDetail, Long> {
    List<RuangDetail> findAllByRuang_Id(Long id);
}
