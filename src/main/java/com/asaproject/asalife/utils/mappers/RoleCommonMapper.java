package com.asaproject.asalife.utils.mappers;

import com.asaproject.asalife.domains.ERole;
import com.asaproject.asalife.domains.ERoleRegister;
import com.asaproject.asalife.domains.ERoleUserRegister;
import com.asaproject.asalife.domains.entities.Role;

import java.util.Collection;
import java.util.stream.Collectors;

public final class RoleCommonMapper {
    public static ERole mapRole(ERoleRegister roleRegister) {
        switch (roleRegister) {
            case CUS:
                return ERole.ROLE_CUS;
            case MT:
                return ERole.ROLE_MT;
            case HK:
                return ERole.ROLE_HK;
            case SPV:
                return ERole.ROLE_SPV;
            case GS:
                return ERole.ROLE_GS;
            case HCGS:
                return ERole.ROLE_HCGS;
            case PROG:
                return ERole.ROLE_PROG;
            default:
                return null;
        }
    }

    public static Collection<ERole> rolesToERoles(Collection<Role> roles) {
        return roles.stream().map(Role::getName).collect(Collectors.toList());
    }
}
