package com.ez.sendem.db.tables;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Table_Scheduled extends RealmObject {

    @PrimaryKey
    private int sch_id;
    private String sch_msg;

    public int getSch_id() {
        return sch_id;
    }

    public void setSch_id(int sch_id) {
        this.sch_id = sch_id;
    }

    public String getSch_msg() {
        return sch_msg;
    }

    public void setSch_msg(String sch_msg) {
        this.sch_msg = sch_msg;
    }

}
