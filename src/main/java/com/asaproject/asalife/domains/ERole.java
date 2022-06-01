package com.asaproject.asalife.domains;

public enum ERole {
    ROLE_ADMIN(Constants.ADMIN),
    ROLE_USER(Constants.USER),

    ROLE_CUSTOMER(Constants.CUSTOMER),
    ROLE_WORKER(Constants.WORKER),
    ROLE_SUPERUSER(Constants.SUPERUSER),
    ROLE_MEGAUSER(Constants.MEGAUSER),

    ROLE_CUS(Constants.CUS),
    ROLE_MT(Constants.MT),
    ROLE_HK(Constants.HK),
    ROLE_SPV(Constants.SPV),
    ROLE_GS(Constants.GS),
    ROLE_HCGS(Constants.HCGS),
    ROLE_PROG(Constants.PROG);

    ERole(String roleString) {
    }

    public static class Constants {
        public static final String ADMIN = "ROLE_ADMIN";
        public static final String USER = "ROLE_USER";

        public static final String CUSTOMER = "ROLE_CUSTOMER";
        public static final String WORKER = "ROLE_WORKER";
        public static final String SUPERUSER = "ROLE_SUPERUSER";
        public static final String MEGAUSER = "ROLE_MEGAUSER";

        public static final String CUS = "ROLE_CUS";
        public static final String MT = "ROLE_MT";
        public static final String HK = "ROLE_HK";
        public static final String SPV = "ROLE_SPV";
        public static final String GS = "ROLE_GS";
        public static final String HCGS = "ROLE_HCGS";
        public static final String PROG = "ROLE_PROG";
    }
}
