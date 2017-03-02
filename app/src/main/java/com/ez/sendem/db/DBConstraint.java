package com.ez.sendem.db;

public class DBConstraint {

    public static class SCHEDULE_RECIPIENT_TYPE {
        public static int SMS = 0;
        public static int WA = 1;
        public static int LINE = 2;
    }

    public static class SCHEDULE_STATUS {
        public static int ACTIVE = 0;
        public static int INACTIVE = 1;
        public static int EXPIRED = 2;
    }
}
