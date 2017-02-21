package com.ez.sendem.db;

import org.json.JSONArray;

import io.realm.Realm;
import io.realm.RealmObject;

public class RealmBaseHelper {
    public static void insertDB(Realm realm, Class myClass, JSONArray str){
        try{
            realm.beginTransaction();
            realm.createOrUpdateAllFromJson(myClass, str);
            realm.commitTransaction();
        }catch (Exception e){
            e.printStackTrace();
            realm.cancelTransaction();
        }
    }

    public static void insertDB(Realm realm, RealmObject object){
        try{
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(object);
            realm.commitTransaction();
        }catch (Exception e){
            e.printStackTrace();
            realm.cancelTransaction();
        }
    }

    public static void insertDB(Realm realm, RealmObject[] object){
        try{
            realm.beginTransaction();
            for(int a=0; a<object.length; a++) {
                realm.copyToRealmOrUpdate(object[a]);
            }
            realm.commitTransaction();
        }catch (Exception e){
            e.printStackTrace();
            realm.cancelTransaction();
        }
    }

    public static void deleteObject(Realm realm, final RealmObject realmObject){
        try{
            realm.beginTransaction();
            realmObject.deleteFromRealm();
            realm.commitTransaction();
        }catch (Exception e){
            e.printStackTrace();
            realm.cancelTransaction();
        }
    }

    public static void deleteObject(Realm realm, final RealmObject[] realmObject){
        try{
            realm.beginTransaction();
            for(int a=0; a<realmObject.length; a++) {
                realmObject[a].deleteFromRealm();
            }
            realm.commitTransaction();
        }catch (Exception e){
            e.printStackTrace();
            realm.cancelTransaction();
        }
    }

    public static void deleteAll(Realm realm){
        try{
            realm.beginTransaction();
            realm.deleteAll();
            realm.commitTransaction();
        }catch (Exception e){
            e.printStackTrace();
            realm.cancelTransaction();
        }
    }
}