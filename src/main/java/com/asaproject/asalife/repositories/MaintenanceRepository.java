package com.asaproject.asalife.repositories;

import com.asaproject.asalife.domains.entities.Maintenance;
import com.asaproject.asalife.domains.entities.User;
import com.asaproject.asalife.domains.models.interfaces.CountByMonth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {
    List<Maintenance> findAllByUserOrderByCreatedAtAsc(User user);

    List<Maintenance> findAllByPicNrpOrderByCreatedAtAsc(String picNrp);

    @Query(value = "SELECT * FROM Maintenance c WHERE c.id = :id", nativeQuery = true)
    Maintenance findMaintenanceByIdNative(@Param("id") Long id);

    @Query(value = "SELECT * FROM Maintenance c ORDER BY c.created_at ASC", nativeQuery = true)
    List<Maintenance> findAllAndOrder();

    @Query(value = "SELECT COUNT(c) FROM Maintenance c WHERE c.status='CLOSED'", nativeQuery = true)
    Long countClosed();

    @Query(value = "SELECT COUNT(c) FROM Maintenance c WHERE c.status!='CLOSED'", nativeQuery = true)
    Long countOngoing();

    @Query(value = "SELECT m.dte, m.tahun, m.bulan, count(id) AS total  " +
            "FROM (SELECT DATE_TRUNC('month', NOW() - interval '4 month') as dte, DATE_PART('year', NOW() - interval '4 month') AS tahun, DATE_PART('month', NOW() - interval '4 month') AS bulan UNION ALL " +
            "SELECT DATE_TRUNC('month', NOW() - interval '3 month') as dte, DATE_PART('year', NOW() - interval '3 month') AS tahun, DATE_PART('month', NOW() - interval '3 month') AS bulan UNION ALL " +
            "SELECT DATE_TRUNC('month', NOW() - interval '2 month') as dte, DATE_PART('year', NOW() - interval '2 month') AS tahun, DATE_PART('month', NOW() - interval '2 month') AS bulan UNION ALL " +
            "SELECT DATE_TRUNC('month', NOW() - interval '1 month') as dte, DATE_PART('year', NOW() - interval '1 month') AS tahun, DATE_PART('month', NOW() - interval '1 month') AS bulan UNION ALL " +
            "SELECT DATE_TRUNC('month', NOW()) as dte, DATE_PART('year', NOW()) AS tahun, DATE_PART('month', NOW()) AS bulan " +
            ") as m LEFT JOIN Maintenance  c " +
            "ON DATE_TRUNC('month', c.created_at) = m.dte " +
            "WHERE m.dte >=  DATE_TRUNC('month', NOW() - interval '5 month') " +
            "GROUP BY 1, 2, 3 " +
            "ORDER BY 1 DESC, 2 DESC, 3 DESC ", nativeQuery = true)
    List<CountByMonth> countByMonth();

    @Query(value = "SELECT count(c.id) FROM Maintenance c WHERE c.status = :status", nativeQuery = true)
    Long countMaintenanceByStatus(@Param("status") String status);
}
