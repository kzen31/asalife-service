package com.asaproject.asalife.utils.mappers;

import com.asaproject.asalife.domains.ETaskMaintenanceStatus;

public class StatusTaskMaintenanceMapper {
    public static String mapStatus(ETaskMaintenanceStatus taskMaintenanceStatus) {
        switch (taskMaintenanceStatus) {
            case BAGUS:
                return "BAGUS";
            case RUSAK:
                return "RUSAK";
            default:
                return null;
        }
    }
}
