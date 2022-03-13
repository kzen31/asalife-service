package com.asaproject.asalife.utils.mappers;

import com.asaproject.asalife.domains.ERole;
import com.asaproject.asalife.domains.ERoleAdminRegister;
import com.asaproject.asalife.domains.entities.Role;

import java.util.Collection;
import java.util.stream.Collectors;

public final class RoleAdminMapper {
    public static ERole mapRole(ERoleAdminRegister roleRegister) {
        switch (roleRegister) {
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
