package com.ez.sendem.db.tables;

import io.realm.RealmObject;

public class Table_Recipient extends RealmObject {
    private String phoneNumber;
    private String info;

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
