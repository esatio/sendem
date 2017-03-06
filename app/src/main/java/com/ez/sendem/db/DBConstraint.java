package com.ez.sendem.db;

/*
note untuk class ini:
-. class ini berisi semua constraint yang digunakan di database
 */
public class DBConstraint {

    public static class SCHEDULE_RECIPIENT_TYPE {
        public static int SMS = 0;
        public static int WA = 1;
        public static int LINE = 2;
    }

    /*
    comment: jika ubah SCHEDULE_STATUS, ingat cek lagi di string.xml
     */
    public static class SCHEDULE_STATUS {
        public static int ACTIVE = 0;
        public static int INACTIVE = 1;
        public static int EXPIRED = 2;
    }

    /*
    comment: jika ubah REPEAT_TYPE, ingat cek lagi di string.xml
     */
    public static class REPEAT_TYPE {
        public static int NONE = 0;
        public static int DAILY = 1;
        public static int WEEKLY = 2;
        public static int MONTHLY = 3;
        public static int YEARLY = 4;
    }

    /*
   comment: jika ubah END_TYPE, ingat cek lagi di string.xml
    */
    public static class END_TYPE {
        public static int NEVER = 0;
        public static int AFTER = 1;
        public static int ON = 2;
    }

    public static class SCHEDULE_SEND_STATUS {
        public static int NONE = 0;
        public static int SENDING = 1;
        public static int SENT = 2;
        public static int FAILED = 3;
    }
}
