package com.asaproject.asalife.repositories;

import com.asaproject.asalife.domains.entities.RatingCatering;
import com.asaproject.asalife.domains.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RatingCateringRepository extends JpaRepository<RatingCatering, Long> {

    @Query(value = "SELECT * FROM RatingCatering c WHERE c.user_id = :id " +
            "ORDER BY c.created_at DESC LIMIT 1", nativeQuery = true)
    RatingCatering findRatingCateringLastAdByUser(@Param("id") Long id);

    List<RatingCatering> findAllByUser(User user);

    @Query(value = "SELECT * FROM RatingCatering c " +
            "ORDER BY c.created_at ASC ", nativeQuery = true)
    List<RatingCatering> findAllAndOrder();

    @Query(value = "SELECT * FROM RatingCatering c WHERE c.user_id = :id " +
            "ORDER BY c.created_at ASC ", nativeQuery = true)
    List<RatingCatering> findAllByUserAndOrder(@Param("id") Long id);
}
