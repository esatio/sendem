package com.ez.sendem.db;

import io.realm.Realm;
import io.realm.RealmObject;

public class RealmHelper {

    public static Realm getRealm()
    {
        Realm realm = Realm.getDefaultInstance();
        return realm;
    }

    public static void insertDB(RealmObject realmObject){
        RealmBaseHelper.insertDB(getRealm(), realmObject);
    }

    public static void insertDB(RealmObject[] realmObject){
        RealmBaseHelper.insertDB(getRealm(), realmObject);
    }

    public static void deleteRealmObject(RealmObject realmObject){
        if(realmObject!=null) {
            RealmBaseHelper.deleteObject(getRealm(), realmObject);
        }
    }

    public static void deleteRealmObject(RealmObject[] realmObject){
        RealmBaseHelper.deleteObject(getRealm(), realmObject);
    }

    public static void deleteAll(){
        RealmBaseHelper.deleteAll(getRealm());
    }
}
