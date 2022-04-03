package com.asaproject.asalife.repositories;

import com.asaproject.asalife.domains.entities.Catering;
import com.asaproject.asalife.domains.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CateringRepository extends JpaRepository<Catering, Long> {
    List<Catering> findByUserOrderByCreatedAtAsc(User user);

    List<Catering> findAllByStatusOrderByCreatedAtAsc(String status);

    List<Catering> findAllByOrderByCreatedAtAsc();

    @Query(value = "SELECT * FROM Catering c WHERE c.id = :id", nativeQuery = true)
    Catering findCateringByIdNative(@Param("id") Long id);
}
