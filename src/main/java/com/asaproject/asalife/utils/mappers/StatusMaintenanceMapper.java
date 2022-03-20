package com.asaproject.asalife.utils.mappers;

import com.asaproject.asalife.domains.ECateringStatus;
import com.asaproject.asalife.domains.EMaintenanceStatus;
import com.asaproject.asalife.domains.ERole;
import com.asaproject.asalife.domains.ERoleAdminRegister;
import com.asaproject.asalife.domains.entities.Role;

import java.util.Collection;
import java.util.stream.Collectors;

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
