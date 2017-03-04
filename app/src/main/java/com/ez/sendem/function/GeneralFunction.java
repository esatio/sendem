package com.ez.sendem.function;

import android.content.Context;
import android.os.Build;
import android.widget.Toast;

import com.ez.sendem.db.DBConstraint;
import com.ez.sendem.db.RealmMainHelper;
import com.ez.sendem.db.tables.Table_Scheduled;
import com.ez.sendem.object.ContactData;
import com.ez.sendem.services.MyService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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
                            SMSFunction.sendSMS(context, phoneNumber, text);
                            ContactData contactData = ContactFunction.getPhoneContactInfo(context, phoneNumber);
                            if(contactData==null){
                                recipient += phoneNumber+"; ";
                            } else {
                                recipient += phoneNumber+"("+contactData.displayName+"); ";
                            }
                        }
                    }
                    NotifFunction.generateAndroidNotif(context, "Message has been sent to "+recipient);
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
                } else if(schedules[a].getSch_repeat_type() == DBConstraint.REPEAT_TYPE.DAILY){
                    schedules[a].setSch_next_active(schedules[a].getSch_next_active() + (24*60*60*1000));
                    schedules[a].setSch_status(DBConstraint.SCHEDULE_STATUS.ACTIVE);
                } else if(schedules[a].getSch_repeat_type() == DBConstraint.REPEAT_TYPE.WEEKLY){
                    schedules[a].setSch_next_active(schedules[a].getSch_next_active() + (7*24*60*60*1000));
                    schedules[a].setSch_status(DBConstraint.SCHEDULE_STATUS.ACTIVE);
                } else if(schedules[a].getSch_repeat_type() == DBConstraint.REPEAT_TYPE.MONTHLY){
                    schedules[a].setSch_next_active(getCurrentDatePlusMonth(schedules[a].getSch_next_active(), 1).getTime());
                    schedules[a].setSch_status(DBConstraint.SCHEDULE_STATUS.ACTIVE);
                } else if(schedules[a].getSch_repeat_type() == DBConstraint.REPEAT_TYPE.YEARLY){
                    schedules[a].setSch_next_active(getCurrentDatePlusYear(schedules[a].getSch_next_active(), 1).getTime());
                    schedules[a].setSch_status(DBConstraint.SCHEDULE_STATUS.ACTIVE);
                }
            }
            RealmMainHelper.getRealm().commitTransaction();
        }catch (Exception e){
            e.printStackTrace();
            RealmMainHelper.getRealm().cancelTransaction();
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
