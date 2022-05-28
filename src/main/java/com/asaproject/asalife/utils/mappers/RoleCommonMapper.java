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
            case CUSTOMER:
                return ERole.ROLE_CUSTOMER;
            case WORKER:
                return ERole.ROLE_WORKER;
            case MEGAUSER:
                return ERole.ROLE_MEGAUSER;
            case SUPERUSER:
                return ERole.ROLE_SUPERUSER;
            default:
                return null;
        }
    }

    public static Collection<ERole> rolesToERoles(Collection<Role> roles) {
        return roles.stream().map(Role::getName).collect(Collectors.toList());
    }
}
