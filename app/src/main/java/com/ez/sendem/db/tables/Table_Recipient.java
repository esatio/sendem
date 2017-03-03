package com.ez.sendem.db.tables;

import io.realm.RealmObject;
/*
//comment : db - 1
    -. table realm harus ditandai dengan extends RealmObject
*/
public class Table_Recipient extends RealmObject {
    /*
    //comment : db - 2
        -. list kolom di table
    */
    private String phoneNumber;
    private String info;

    /*
    //comment : db - 3
        -. buat function set dan get untuk semua kolom
    */
    public String phoneNumber(){
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public String info(){
        return info;
    }

    public void setInfo(String info){
        this.info = info;
    }

}
