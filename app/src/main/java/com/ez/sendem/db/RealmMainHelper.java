package com.ez.sendem.db;

import org.json.JSONArray;

import io.realm.Realm;
import io.realm.RealmObject;

public class RealmMainHelper {

    public static Realm getRealm()
    {
        Realm realm = Realm.getDefaultInstance();
        return realm;
    }

    public static void insertDB(Class realmClass, JSONArray str){
        RealmBaseHelper.insertDB(getRealm(), realmClass, str);
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

    public static int getPrimaryKey(Class realmObjectClass, String primaryKey){
        Number currentIdNum = RealmMainHelper.getRealm().where(realmObjectClass).max(primaryKey);
        int nextId;
        if(currentIdNum == null) {
            nextId = 1;
        } else {
            nextId = currentIdNum.intValue() + 1;
        }
        return nextId;
    }
}
