package com.ez.sendem.ui;

import android.app.Application;
import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.setDefaultConfiguration(getRealmConfig(this)); // Make this Realm the default
    }

    private static RealmConfiguration getRealmConfig(Context context){
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(context)
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
        return realmConfig;
    }
}
