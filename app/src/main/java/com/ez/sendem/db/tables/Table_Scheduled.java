package com.ez.sendem.db.tables;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/*
//comment : db - 1
    -. table realm harus ditandai dengan extends RealmObject
*/
public class Table_Scheduled extends RealmObject {
    /*
    //comment : db - 2
        -. list kolom di table
        -. untuk primary key, tambahkan "@PrimaryKey"
    */
    @PrimaryKey
    private int sch_id;
    private String sch_msg;
    private int sch_recipient_type;
    private long sch_date; //comment: tanggal yang diset oleh pengguna
    private long sch_next_active; //comment: tanggal selanjutnya alarm aktif
    private int sch_repeat_type;
    private int sch_ends_on;
    private RealmList<Table_Recipient> recipients; //comment: list di table, harus menggunakan RealmList (tidak boleh menggunakan List android)
    private int sch_status; //comment: untuk menentukan apakah masih aktif atau ga

    /*
    //comment : db - 3
        -. untuk variable atau function yang tidak berhubungan sama table (sebagai pendukung saja), harus ditambahkan "@Ignore"
        -. jika tidak ditambahkan "@Ignore", akan error
    */
    @Ignore
    public static String PRIMARY_KEY = "sch_id";
    @Ignore
    public static String NEXT_ACTIVE_DATE = "sch_next_active";
    @Ignore
    public static String STATUS = "sch_status";

    /*
    //comment : db - 4
        -. buat function set dan get untuk semua kolom
    */
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


    public long getSch_next_active(){
        return sch_next_active;
    }

    public void setSch_next_active(long sch_next_active){
        this.sch_next_active = sch_next_active;
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
