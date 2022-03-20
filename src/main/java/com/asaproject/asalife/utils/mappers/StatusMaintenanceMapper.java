package com.asaproject.asalife.utils.mappers;

import com.asaproject.asalife.domains.EMaintenanceStatus;

public final class StatusMaintenanceMapper {
    public static String mapStatus(EMaintenanceStatus maintenanceStatus) {
        switch (maintenanceStatus) {
            case OPEN:
                return "OPEN";
            case PROGRESS:
                return "PROGRESS";
            case CLOSED:
                return "CLOSED";
            case HOLD:
                return "HOLD";
            default:
                return null;
        }
    }
}
