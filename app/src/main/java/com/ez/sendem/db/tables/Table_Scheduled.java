package com.ez.sendem.db.tables;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class Table_Scheduled extends RealmObject {

    @PrimaryKey
    private int sch_id;
    private String sch_msg;
    private int sch_recipient_type;
    private long sch_date;
    private int sch_repeat_type;
    private int sch_ends_on;
    private RealmList<Table_Recipient> recipients;
    private int sch_status; //comment: untuk menentukan apakah masih aktif atau ga

    @Ignore
    public static String PRIMARY_KEY = "sch_id";
    @Ignore
    public static String DATE = "sch_date";
    @Ignore
    public static String STATUS = "sch_status";

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

    public int getSch_recipient_type() {
        return sch_recipient_type;
    }

    public void setSch_recipient_type(int sch_recipient_type) {
        this.sch_recipient_type = sch_recipient_type;
    }

    /*

    private Date sch_date;
    private int sch_repeat_type;
    private int sch_ends_on;
     */

    public long getSch_date(){
        return sch_date;
    }

    public void setSch_date(long sch_date){
        this.sch_date = sch_date;
    }

    public int getSch_repeat_type(){
        return sch_repeat_type;
    }

    public void setSch_repeat_type(int sch_repeat_type){
        this.sch_repeat_type = sch_repeat_type;
    }

    public int getSch_ends_on(){
        return sch_ends_on;
    }

    public void setSch_ends_on(int sch_ends_on){
        this.sch_ends_on = sch_ends_on;
    }

    public RealmList<Table_Recipient> getRecipients() {
        return recipients;
    }

    public void setRecipients(RealmList<Table_Recipient> recipients) {
        this.recipients = recipients;
    }

    public int getSch_status(){
        return this.sch_status;
    }

    public void setSch_status(int sch_status){
        this.sch_status = sch_status;
    }

}
