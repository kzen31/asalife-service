package com.asaproject.asalife.utils.mappers;

import com.asaproject.asalife.domains.ERole;
import com.asaproject.asalife.domains.ERoleUserRegister;
import com.asaproject.asalife.domains.entities.Role;

import java.util.Collection;
import java.util.stream.Collectors;

public final class RoleUserMapper {
    public static ERole mapRole(ERoleUserRegister roleRegister) {
        switch (roleRegister) {
            case CUSTOMER:
                return ERole.ROLE_CUSTOMER;
            case WORKER:
                return ERole.ROLE_WORKER;
            default:
                return null;
        }
    }

    public static Collection<ERole> rolesToERoles(Collection<Role> roles) {
        return roles.stream().map(Role::getName).collect(Collectors.toList());
    }
}
