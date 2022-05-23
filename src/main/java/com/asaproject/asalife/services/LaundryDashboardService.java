package com.asaproject.asalife.services;


import com.asaproject.asalife.domains.models.responses.RecordDashboard;

import java.util.List;

public interface LaundryDashboardService {
    List<RecordDashboard> countEveryStatusLaundry();

    RecordDashboard countByStatusLaundry(String status, String iconClass) ;
}
