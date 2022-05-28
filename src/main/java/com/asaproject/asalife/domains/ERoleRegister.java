package com.asaproject.asalife.domains;

import java.util.EnumSet;

public enum ERoleRegister {
    SUPERUSER,
    MEGAUSER,
    CUSTOMER,
    WORKER;

    public static EnumSet<ERoleRegister> getAdmin() {
        return EnumSet.of(SUPERUSER, MEGAUSER);
    }

    public static EnumSet<ERoleRegister> getUser() {
        return EnumSet.of(CUSTOMER, WORKER);
    }
}
