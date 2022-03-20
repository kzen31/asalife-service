package com.asaproject.asalife.utils.mappers;

import com.asaproject.asalife.domains.ECateringStatus;
import com.asaproject.asalife.domains.ERole;
import com.asaproject.asalife.domains.ERoleUserRegister;
import com.asaproject.asalife.domains.entities.Role;

import java.util.Collection;
import java.util.stream.Collectors;

public final class StatusCateringUserMapper {
    public static String mapStatus(ECateringStatus cateringStatus) {
        switch (cateringStatus) {
            case INQUIRY:
                return "INQUIRY";
            case INVESTIGATION:
                return "INVESTIGATION";
            case CLOSED:
                return "CLOSED";
            default:
                return null;
        }
    }
}
