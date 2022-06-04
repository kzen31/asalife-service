package com.asaproject.asalife.repositories;

import com.asaproject.asalife.domains.entities.Housekeeping;
import com.asaproject.asalife.domains.entities.Laundry;
import com.asaproject.asalife.domains.entities.RatingCatering;
import com.asaproject.asalife.domains.entities.User;
import com.asaproject.asalife.domains.models.interfaces.CountByMonth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LaundryRepository extends JpaRepository<Laundry, Long> {
    List<Laundry> findAllByUser(User user);

    List<Laundry> findAllByOrderByCreatedAtAsc();

    @Query(value = "SELECT * FROM Laundry c  WHERE c.user_id = :id " +
            "ORDER BY c.created_at ASC ", nativeQuery = true)
    List<Laundry> findAllByUserAndOrder(@Param("id") Long id);

    @Query(value = "SELECT * FROM Laundry c WHERE c.id = :id", nativeQuery = true)
    Laundry findLaundryByIdNative(@Param("id") Long id);

    @Query(value = "SELECT * FROM Laundry c " +
            "ORDER BY c.created_at ASC ", nativeQuery = true)
    List<Laundry> findAllAndOrder();

    @Query(value = "SELECT COUNT(c) FROM Laundry c WHERE c.status='DONE' AND c.deleted_at IS NULL ", nativeQuery = true)
    Long countClosed();

    @Query(value = "SELECT COUNT(c) FROM Laundry c WHERE c.status!='DONE' AND c.deleted_at IS NULL ", nativeQuery = true)
    Long countOngoing();

    @Query(value = "SELECT m.dte, m.tahun, m.bulan, count(id) AS total  " +
            "FROM (SELECT DATE_TRUNC('month', NOW() - interval '4 month') as dte, DATE_PART('year', NOW() - interval '4 month') AS tahun, DATE_PART('month', NOW() - interval '4 month') AS bulan UNION ALL " +
            "SELECT DATE_TRUNC('month', NOW() - interval '3 month') as dte, DATE_PART('year', NOW() - interval '3 month') AS tahun, DATE_PART('month', NOW() - interval '3 month') AS bulan UNION ALL " +
            "SELECT DATE_TRUNC('month', NOW() - interval '2 month') as dte, DATE_PART('year', NOW() - interval '2 month') AS tahun, DATE_PART('month', NOW() - interval '2 month') AS bulan UNION ALL " +
            "SELECT DATE_TRUNC('month', NOW() - interval '1 month') as dte, DATE_PART('year', NOW() - interval '1 month') AS tahun, DATE_PART('month', NOW() - interval '1 month') AS bulan UNION ALL " +
            "SELECT DATE_TRUNC('month', NOW()) as dte, DATE_PART('year', NOW()) AS tahun, DATE_PART('month', NOW()) AS bulan " +
            ") as m LEFT JOIN Laundry  c " +
            "ON DATE_TRUNC('month', c.created_at) = m.dte " +
            "WHERE m.dte >=  DATE_TRUNC('month', NOW() - interval '5 month')  AND c.deleted_at IS NULL " +
            "GROUP BY 1, 2, 3 " +
            "ORDER BY 1 DESC, 2 DESC, 3 DESC ", nativeQuery = true)
    List<CountByMonth> countByMonth();

    @Query(value = "SELECT count(c.id) FROM Laundry c WHERE c.status = :status AND c.deleted_at IS NULL ", nativeQuery = true)
    Long countLaundryByStatus(@Param("status") String status);
}
