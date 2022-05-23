package com.asaproject.asalife.services;

import com.asaproject.asalife.domains.models.responses.RecordDashboard;
import com.asaproject.asalife.repositories.CateringRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CateringDashboardServiceImpl implements CateringDashboardService{
    private final CateringRepository cateringRepo;

    @Override
    public List<RecordDashboard> countEveryStatusCatering() {
        List<RecordDashboard> recordDashboardList = new ArrayList<>();

        recordDashboardList.add(countByStatusCatering("INQUIRY", "food-fork-drink"));
        recordDashboardList.add(countByStatusCatering("INVESTIGATION", "food-fork-drink"));
        recordDashboardList.add(countByStatusCatering("CLOSED", "food-fork-drink"));

        return recordDashboardList;
    }

    @Override
    public RecordDashboard countByStatusCatering(String status, String iconClass) {
        RecordDashboard recordDashboard = new RecordDashboard();

        recordDashboard.setTotal(cateringRepo.countCateringByStatus(status));
        recordDashboard.setIconClass(iconClass);
        recordDashboard.setTitle(status);

        return recordDashboard;
    }
}
