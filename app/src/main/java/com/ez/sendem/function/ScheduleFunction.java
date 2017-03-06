package com.ez.sendem.function;

import com.ez.sendem.db.DBConstraint;
import com.ez.sendem.db.RealmMainHelper;
import com.ez.sendem.db.tables.Table_Scheduled;

import java.util.ArrayList;
import io.realm.RealmResults;

public class ScheduleFunction {
    public static ArrayList<Table_Scheduled> getNextSchedule(){
        ArrayList<Table_Scheduled> table_scheduled = new ArrayList<>();

        RealmResults<Table_Scheduled> results = RealmMainHelper.getRealm()
                                                    .where(Table_Scheduled.class)
                                                    .equalTo(Table_Scheduled.STATUS, DBConstraint.SCHEDULE_STATUS.ACTIVE)
                                                    .findAllSorted(Table_Scheduled.NEXT_ACTIVE_DATE);

        long nextTimeMillis=0;
        if(results.size()>0){
            nextTimeMillis = results.get(0).getSch_next_active();
            table_scheduled.add(results.get(0));
        }
        boolean isCheckNext = true;
        for(int a=1; a<results.size() && isCheckNext; a++){
            if(results.get(a).getSch_next_active() == nextTimeMillis){
                table_scheduled.add(results.get(a));
            } else {
                isCheckNext = false;
            }
        }
        return table_scheduled;
    }
}
