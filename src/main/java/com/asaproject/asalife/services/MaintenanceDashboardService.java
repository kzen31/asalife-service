package com.asaproject.asalife.services;


import com.asaproject.asalife.domains.models.responses.RecordDashboard;

import java.util.List;

public interface MaintenanceDashboardService {
    List<RecordDashboard> countEveryStatusMaintenance();

    RecordDashboard countByStatusMaintenance(String status, String iconClass) ;
}
