package com.asaproject.asalife.utils.mappers;

import com.asaproject.asalife.domains.ELaundryStatus;

public final class StatusLaundryUserMapper {
    public static String mapStatus(ELaundryStatus eLaundryStatus) {
        switch (eLaundryStatus) {
            case SEARCHING:
                return "SEARCHING";
            case COMPENSATION:
                return "COMPENSATION";
            case DONE:
                return "DONE";
            default:
                return null;
        }
    }
}
