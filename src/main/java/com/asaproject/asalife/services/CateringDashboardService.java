package com.asaproject.asalife.services;


import com.asaproject.asalife.domains.models.responses.RecordDashboard;

import java.util.List;

public interface CateringDashboardService {
    List<RecordDashboard> countEveryStatusCatering();

    RecordDashboard countByStatusCatering(String status, String iconClass) ;
}
