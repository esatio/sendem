package com.ez.sendem.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

import com.ez.sendem.db.RealmMainHelper;
import com.ez.sendem.db.tables.Table_Scheduled;
import com.ez.sendem.function.AlarmFunction;
import com.ez.sendem.function.ScheduleFunction;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmChangeListener;

public class MyService extends Service{

    public static ArrayList<Table_Scheduled> nextSchedule = new ArrayList<>();
    private RealmChangeListener listener;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        refreshAlarm();

        listener = getListener();
        RealmMainHelper.getRealm().addChangeListener(listener);
    }

    private RealmChangeListener getListener(){
        return (
            new RealmChangeListener<Realm>() {
                @Override
                public void onChange(Realm element) {
                    refreshAlarm();
                }
            }
        );
    }

    private void refreshAlarm(){
        nextSchedule = ScheduleFunction.getNextSchedule();
        if(nextSchedule!=null && nextSchedule.size()>0){
            AlarmFunction.setAlarm(this, nextSchedule.get(0).getSch_date());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Intent restartService = new Intent(getApplicationContext(), this.getClass());
        restartService.setPackage(getPackageName());
        PendingIntent restartServicePI = PendingIntent.getService(
                getApplicationContext(), 1, restartService,
                PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmService = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmService.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() +1000, restartServicePI);
    }
}
