package com.asaproject.asalife.utils.mappers;

import com.asaproject.asalife.domains.EHouseKeepingStatus;

public final class StatusHousekeepingUserMapper {
    public static String mapStatus(EHouseKeepingStatus houseKeepingStatus) {
        switch (houseKeepingStatus) {
            case CLEANING_PROGRESS:
                return "CLEANING_PROGRESS";
            case DONE:
                return "DONE";
            default:
                return null;
        }
    }


}
