package com.asaproject.asalife.services;

import com.asaproject.asalife.domains.models.responses.RecordDashboard;
import com.asaproject.asalife.repositories.CateringRepository;
import com.asaproject.asalife.repositories.MaintenanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MaintenanceDashboardServiceImpl implements MaintenanceDashboardService{
    private final MaintenanceRepository maintenanceRepository;

    @Override
    public List<RecordDashboard> countEveryStatusMaintenance() {
        List<RecordDashboard> recordDashboardList = new ArrayList<>();

        recordDashboardList.add(countByStatusMaintenance("OPEN", "tools"));
        recordDashboardList.add(countByStatusMaintenance("PROGRESS", "tools"));
        recordDashboardList.add(countByStatusMaintenance("HOLD", "tools"));
        recordDashboardList.add(countByStatusMaintenance("CLOSED", "tools"));

        return recordDashboardList;
    }

    @Override
    public RecordDashboard countByStatusMaintenance(String status, String iconClass) {
        RecordDashboard recordDashboard = new RecordDashboard();

        recordDashboard.setTotal(maintenanceRepository.countMaintenanceByStatus(status));
        recordDashboard.setIconClass(iconClass);
        recordDashboard.setTitle(status);

        return recordDashboard;
    }
}
