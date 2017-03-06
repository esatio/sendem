package com.ez.sendem.function;

import android.content.Context;
import android.os.Build;
import android.widget.Toast;

import com.ez.sendem.db.DBConstraint;
import com.ez.sendem.db.RealmMainHelper;
import com.ez.sendem.db.tables.Table_History;
import com.ez.sendem.db.tables.Table_Scheduled;
import com.ez.sendem.object.ContactData;
import com.ez.sendem.object.SMSObject;
import com.ez.sendem.services.MyService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.realm.RealmResults;

public class GeneralFunction {

    public static String getHourAndMinuteString(int hour, int minute){
        String result = "";
        if(hour<10){
            result = "0"+hour;
        } else {
            result = ""+hour;
        }

        if(minute<10){
            result = result+":0"+minute;
        } else {
            result = result+":"+minute;
        }
        return result;
    }

    public static final String DATE_TIME_DISPLAY_FORMAT="dd MMM yyyy HH:mm";
    public static final String DATE_DISPLAY_FORMAT="dd MMM yyyy";
    public static final String TIME_DISPLAY_FORMAT="HH:mm";
    public static String getDateTime(Date date, String dateTimeFormat){
        SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);
        String dateString = sdf.format(date);
        return dateString;
    }

    public static Date parseStringToDate(String dateTime, String oldFormat){
        Date date=null;

        SimpleDateFormat format = new SimpleDateFormat(oldFormat);
        try {
            date = format.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static int getDeviceApiLevel(){
        return Build.VERSION.SDK_INT;
    }

    public static void sendMessage(Context context){
        ArrayList<SMSObject> smsObject = new ArrayList<>();
        int[] arrId = new int[MyService.nextSchedule.size()];
        if(MyService.nextSchedule != null && MyService.nextSchedule.size() > 0){
            for(int a=0; a<MyService.nextSchedule.size(); a++){
                Table_Scheduled schedule = MyService.nextSchedule.get(a);
                if(schedule!=null){
                    arrId[a] = schedule.getSch_id();
                    String recipient = "";
                    if(schedule.getSch_recipient_type() == DBConstraint.SCHEDULE_RECIPIENT_TYPE.SMS){
                        String text = schedule.getSch_msg();
                        for(int b=0; b<schedule.getRecipients().size(); b++){
                            String phoneNumber = schedule.getRecipients().get(b).phoneNumber();
//                            SMSFunction.sendSMS(context, phoneNumber, text);

                            ContactData contactData = ContactFunction.getPhoneContactInfo(context, phoneNumber);
                            if(contactData==null){
                                recipient += phoneNumber+"; ";
                            } else {
                                recipient += phoneNumber+"("+contactData.displayName+"); ";
                            }
                            smsObject.add(new SMSObject(phoneNumber, text));

                            //comment: insert ke table history
                            Table_History history = new Table_History();
                            history.setHst_id(RealmMainHelper.getPrimaryKey(Table_History.class, Table_History.PRIMARY_KEY));
                            history.setRef_sch(schedule);
                            history.setHst_msg(schedule.getSch_msg());
                            history.setHst_send_date(schedule.getSch_next_active());
                            history.setHst_send_status(DBConstraint.SCHEDULE_SEND_STATUS.SENDING);
                            history.setPhoneNumber(phoneNumber);
                            RealmMainHelper.insertDB(history);
                        }
                    }
                    NotifFunction.generateAndroidNotif(context, "Sending message to "+recipient);
                }
            }
        }

        //comment: update table
        Table_Scheduled[] schedules = new Table_Scheduled[arrId.length];
        for(int a=0; a<arrId.length; a++) {
            schedules[a] = RealmMainHelper.getRealm().where(Table_Scheduled.class).equalTo(Table_Scheduled.PRIMARY_KEY, arrId[a]).findFirst();
        }

        try{
            RealmMainHelper.getRealm().beginTransaction();
            for(int a=0; a<schedules.length; a++) {
                if(schedules[a].getSch_repeat_type() == DBConstraint.REPEAT_TYPE.NONE){
                    schedules[a].setSch_next_active(schedules[a].getSch_next_active());
                    schedules[a].setSch_status(DBConstraint.SCHEDULE_STATUS.EXPIRED);
                } else {
                    long next_active_millis = 0;
                    if(schedules[a].getSch_repeat_type() == DBConstraint.REPEAT_TYPE.DAILY){
                        next_active_millis = schedules[a].getSch_next_active() + (24*60*60*1000);
                    } else if(schedules[a].getSch_repeat_type() == DBConstraint.REPEAT_TYPE.WEEKLY){
                        next_active_millis = schedules[a].getSch_next_active() + (7*24*60*60*1000);
                    } else if(schedules[a].getSch_repeat_type() == DBConstraint.REPEAT_TYPE.MONTHLY){
                        next_active_millis = getCurrentDatePlusMonth(schedules[a].getSch_next_active(), 1).getTime();
                    } else if(schedules[a].getSch_repeat_type() == DBConstraint.REPEAT_TYPE.YEARLY){
                        next_active_millis = getCurrentDatePlusYear(schedules[a].getSch_next_active(), 1).getTime();
                    }

                    if(isExpired(next_active_millis, schedules[a].getSch_ends_on())){
                        schedules[a].setSch_status(DBConstraint.SCHEDULE_STATUS.EXPIRED);
                    } else {
                        schedules[a].setSch_next_active(next_active_millis);
                        schedules[a].setSch_status(DBConstraint.SCHEDULE_STATUS.ACTIVE);
                    }
                }
            }
            RealmMainHelper.getRealm().commitTransaction();
        }catch (Exception e){
            e.printStackTrace();
            RealmMainHelper.getRealm().cancelTransaction();
        }

        for(int a=0; a<smsObject.size(); a++){
            Toast.makeText(context, "send SMS to " + smsObject.get(a).phone, Toast.LENGTH_SHORT).show();
            SMSFunction.sendSMS(context, smsObject.get(a).phone, smsObject.get(a).text);
        }
    }

    private static boolean isExpired(long next_active_millis, long endsOn){
        if(endsOn == 0){
            return false;
        } else if(next_active_millis > endsOn){
            return true;
        } else{
            return false;
        }
    }

    public static void changeHistorySentStatus(int schSendStatus){
        RealmResults<Table_History> result = RealmMainHelper.getRealm()
                                .where(Table_History.class)
                                .equalTo(Table_History.SEND_STATUS, DBConstraint.SCHEDULE_SEND_STATUS.SENDING)
                                .findAllSorted(Table_History.PRIMARY_KEY);
        if(result.size()>0){
            try{
                RealmMainHelper.getRealm().beginTransaction();
                result.get(0).setHst_send_status(schSendStatus);
                RealmMainHelper.getRealm().commitTransaction();
            }catch (Exception e){
                e.printStackTrace();
                RealmMainHelper.getRealm().cancelTransaction();
            }
        }
    }

    public static Date getCurrentDatePlusMonth(long date, int month)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        calendar.add(Calendar.MONTH, month);
        Date newDate = calendar.getTime();
        return newDate;
    }

    public static Date getCurrentDatePlusYear(long date, int year)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        calendar.add(Calendar.YEAR, year);
        Date newDate = calendar.getTime();
        return newDate;
    }
}
