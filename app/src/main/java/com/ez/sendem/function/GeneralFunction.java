package com.ez.sendem.function;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.SmsManager;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ez.sendem.broadcastreceiver.SMSDeliveredReceiver;
import com.ez.sendem.broadcastreceiver.SmsSentReceiver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class GeneralFunction {

    public static Date getDateTimeFromPicker(DatePicker datePicker, TimePicker timePicker){
        String str_date = getDateFromDatePicker(datePicker) + " " + getTimeFromTimePicker(timePicker);
        Date date=null;

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            date = format.parse(str_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    //yyyy-MM-dd
    public static String getDateFromDatePicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        Date selectedDate = calendar.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = sdf.format(selectedDate);

        return dateString;
    }

    //HH:mm
    public static String getTimeFromTimePicker(TimePicker timePicker){
        int hour = 0;
        int minute = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hour = timePicker.getHour();
            minute = timePicker.getMinute();
        } else {
            hour = timePicker.getCurrentHour();
            minute = timePicker.getCurrentMinute();

        }
        String result = "";
        if(hour<10){
            result = "0"+hour;
//            result.concat(String.valueOf(hour));
        } else {
            result = ""+hour;
//            String hour_Str = String.valueOf(hour);
//            result.concat(0+""+String.valueOf(hour));
        }

        if(minute<10){
            result = result+":0"+String.valueOf(minute);
        } else {
            result = result+":"+String.valueOf(minute);
        }

        return result;
    }

    public static void sendSMS(Context mContext, String phoneNumber, String message) {
        ArrayList<PendingIntent> sentPendingIntents = new ArrayList<PendingIntent>();
        ArrayList<PendingIntent> deliveredPendingIntents = new ArrayList<PendingIntent>();
        PendingIntent sentPI = PendingIntent.getBroadcast(mContext, 0,
                new Intent(mContext, SmsSentReceiver.class), 0);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(mContext, 0,
                new Intent(mContext, SMSDeliveredReceiver.class), 0);
        try {
            SmsManager sms = SmsManager.getDefault();
            ArrayList<String> mSMSMessage = sms.divideMessage(message);
            for (int i = 0; i < mSMSMessage.size(); i++) {
                sentPendingIntents.add(i, sentPI);
                deliveredPendingIntents.add(i, deliveredPI);
            }
            sms.sendMultipartTextMessage(phoneNumber, null, mSMSMessage,
                    sentPendingIntents, deliveredPendingIntents);
            sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
            Toast.makeText(mContext, "SMS sent",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(mContext, "SMS sending failed...",Toast.LENGTH_SHORT).show();
        }

    }

}
