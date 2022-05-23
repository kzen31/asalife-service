package com.asaproject.asalife.services;

import com.asaproject.asalife.domains.models.responses.RecordDashboard;
import com.asaproject.asalife.repositories.HousekeepingRepository;
import com.asaproject.asalife.repositories.LaundryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class LaundryDashboardServiceImpl implements LaundryDashboardService{
    private final LaundryRepository laundryRepository;

    @Override
    public List<RecordDashboard> countEveryStatusLaundry() {
        List<RecordDashboard> recordDashboardList = new ArrayList<>();

        recordDashboardList.add(countByStatusLaundry("SEARCHING", "water-check"));
        recordDashboardList.add(countByStatusLaundry("COMPENSATION", "water-check"));
        recordDashboardList.add(countByStatusLaundry("DONE", "water-check"));

        return recordDashboardList;
    }

    @Override
    public RecordDashboard countByStatusLaundry(String status, String iconClass) {
        RecordDashboard recordDashboard = new RecordDashboard();

        recordDashboard.setTotal(laundryRepository.countLaundryByStatus(status));
        recordDashboard.setIconClass(iconClass);
        recordDashboard.setTitle(status);

        return recordDashboard;
    }
}
