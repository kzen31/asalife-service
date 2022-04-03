package com.asaproject.asalife.repositories;

import com.asaproject.asalife.domains.entities.Laundry;
import com.asaproject.asalife.domains.entities.RatingCatering;
import com.asaproject.asalife.domains.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LaundryRepository extends JpaRepository<Laundry, Long> {
    List<Laundry> findAllByUser(User user);

    @Query(value = "SELECT * FROM Laundry c  WHERE c.user_id = :id " +
            "ORDER BY c.created_at ASC ", nativeQuery = true)
    List<Laundry> findAllByUserAndOrder(@Param("id") Long id);

    @Query(value = "SELECT * FROM Laundry c WHERE c.id = :id", nativeQuery = true)
    Laundry findLaundryByIdNative(@Param("id") Long id);

    @Query(value = "SELECT * FROM Laundry c " +
            "ORDER BY c.created_at ASC ", nativeQuery = true)
    List<Laundry> findAllAndOrder();
}
