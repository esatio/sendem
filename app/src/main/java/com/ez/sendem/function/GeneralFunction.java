package com.ez.sendem.function;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        } else {
            result = ""+result;
        }

        if(minute<10){
            result += "0"+result;
        } else {
            result += result;
        }

        return result;
    }
}
