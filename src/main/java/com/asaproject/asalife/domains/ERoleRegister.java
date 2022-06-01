package com.asaproject.asalife.domains;

import java.util.EnumSet;

public enum ERoleRegister {
    CUS,
    MT,
    HK,
    SPV,
    GS,
    HCGS,
    PROG;

    public static EnumSet<ERoleRegister> getWorker() {
        return EnumSet.of(MT, HK);
    }

    public static EnumSet<ERoleRegister> getSuperUser() {
        return EnumSet.of(SPV, GS);
    }

    public static EnumSet<ERoleRegister> getMegaUser() {
        return EnumSet.of(HCGS, PROG);
    }
}
