package com.asaproject.asalife.repositories;

import com.asaproject.asalife.domains.entities.RecordHousekeeping;
import com.asaproject.asalife.domains.entities.RuangDetail;
import com.asaproject.asalife.domains.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecordHousekeepingRepository extends JpaRepository<RecordHousekeeping, Long> {
    List<RecordHousekeeping> findAllByUser(User user);

    @Query(value = "SELECT * FROM RecordHousekeeping c WHERE c.id = :id", nativeQuery = true)
    RecordHousekeeping findRecordByIdNative(@Param("id") Long id);
}
