package com.ez.sendem.db.tables;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/*
//comment : db - 1
    -. table realm harus ditandai dengan extends RealmObject
*/
public class Table_History extends RealmObject {
    /*
        //comment : db - 2
            -. list kolom di table
            -. untuk primary key, tambahkan "@PrimaryKey"
        */
    @PrimaryKey
    private int hst_id;
    private Table_Scheduled ref_sch;
    private String hst_msg;
    private long hst_send_date;
    private int hst_send_status; //comment: untuk menentukan apakah sedang mengirim, terkirim, atau gagal
    private String phoneNumber;

    /*
    //comment : db - 3
        -. untuk variable atau function yang tidak berhubungan sama table (sebagai pendukung saja), harus ditambahkan "@Ignore"
        -. jika tidak ditambahkan "@Ignore", akan error
    */
    @Ignore
    public static String PRIMARY_KEY = "hst_id";
    @Ignore
    public static String SEND_STATUS = "hst_send_status";

    /*
    //comment : db - 4
        -. buat function set dan get untuk semua kolom (click alt+insert di varible)
    */

    public int getHst_id() {
        return hst_id;
    }

    public void setHst_id(int hst_id) {
        this.hst_id = hst_id;
    }

    public Table_Scheduled getRef_sch() {
        return ref_sch;
    }

    public void setRef_sch(Table_Scheduled ref_sch) {
        this.ref_sch = ref_sch;
    }

    public String getHst_msg() {
        return hst_msg;
    }

    public void setHst_msg(String hst_msg) {
        this.hst_msg = hst_msg;
    }

    public long getHst_send_date() {
        return hst_send_date;
    }

    public void setHst_send_date(long hst_send_date) {
        this.hst_send_date = hst_send_date;
    }

    public int getHst_send_status() {
        return hst_send_status;
    }

    public void setHst_send_status(int hst_send_status) {
        this.hst_send_status = hst_send_status;
    }

    public String phoneNumber(){
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

}
