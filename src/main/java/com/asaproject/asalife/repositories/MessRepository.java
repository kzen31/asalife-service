package com.asaproject.asalife.repositories;

import com.asaproject.asalife.domains.entities.Mess;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessRepository extends JpaRepository<Mess, Long> {
    Mess findByNameIgnoreCaseAndDeletedAtIsNull(String name);
}
