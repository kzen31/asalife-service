package com.asaproject.asalife.services;

import com.asaproject.asalife.domains.models.responses.RecordDashboard;
import com.asaproject.asalife.repositories.CateringRepository;
import com.asaproject.asalife.repositories.HousekeepingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class HousekeepingDashboardServiceImpl implements HousekeepingDashboardService{
    private final HousekeepingRepository housekeepingRepository;

    @Override
    public List<RecordDashboard> countEveryStatusHousekeeping() {
        List<RecordDashboard> recordDashboardList = new ArrayList<>();

        recordDashboardList.add(countByStatusHousekeeping("CLEANING_PROGRESS", "hand-heart"));
        recordDashboardList.add(countByStatusHousekeeping("DONE", "hand-heart"));

        return recordDashboardList;
    }

    @Override
    public RecordDashboard countByStatusHousekeeping(String status, String iconClass) {
        RecordDashboard recordDashboard = new RecordDashboard();

        recordDashboard.setTotal(housekeepingRepository.countHOusekeepingByStatus(status));
        recordDashboard.setIconClass(iconClass);
        recordDashboard.setTitle(status);

        return recordDashboard;
    }
}
