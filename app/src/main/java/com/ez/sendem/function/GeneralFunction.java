package com.ez.sendem.function;

import android.content.Context;
import android.os.Build;

import com.ez.sendem.db.DBConstraint;
import com.ez.sendem.db.RealmMainHelper;
import com.ez.sendem.db.tables.Table_Scheduled;
import com.ez.sendem.object.ContactData;
import com.ez.sendem.services.MyService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
                    NotifFunction.generateAndroidNotif(context, "Scheduler has been sent message to "+recipient);
                }
            }
        }

        //comment: update table
        Table_Scheduled[] updateTable = new Table_Scheduled[MyService.nextSchedule.size()];
        for(int a=0; a<arrId.length; a++){
            Table_Scheduled schedule = RealmMainHelper.getRealm().where(Table_Scheduled.class).equalTo(Table_Scheduled.PRIMARY_KEY, arrId[a]).findFirst();

            Table_Scheduled newSchedule = new Table_Scheduled();
            newSchedule.setSch_id(schedule.getSch_id());
            newSchedule.setSch_msg(schedule.getSch_msg());
            newSchedule.setSch_recipient_type(schedule.getSch_recipient_type());
            newSchedule.setSch_date(schedule.getSch_date());
            newSchedule.setSch_repeat_type(schedule.getSch_repeat_type());
            newSchedule.setSch_ends_on(schedule.getSch_ends_on());
            newSchedule.setRecipients(schedule.getRecipients());
            newSchedule.setSch_status(DBConstraint.SCHEDULE_STATUS.EXPIRED);
            updateTable[a] = newSchedule;
        }
        RealmMainHelper.insertDB(updateTable);
    }
}
