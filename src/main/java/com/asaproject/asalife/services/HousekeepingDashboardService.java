package com.asaproject.asalife.services;


import com.asaproject.asalife.domains.models.responses.RecordDashboard;

import java.util.List;

public interface HousekeepingDashboardService {
    List<RecordDashboard> countEveryStatusHousekeeping();

    RecordDashboard countByStatusHousekeeping(String status, String iconClass) ;
}
