package com.ez.sendem.function;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import com.ez.sendem.R;

public class NotifFunction {
//    public static void generateAndroidNotif(Context context,int schId){
//        NotificationManager mNotificationManager = (NotificationManager) context
//                .getSystemService(Context.NOTIFICATION_SERVICE);
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
//        mBuilder.setAutoCancel(true);
//        mBuilder.setContentTitle(context.getString(R.string.app_name)).setSmallIcon(R.drawable.ic_launcher);
//        SoundFunction.playNotif(context, mBuilder);
//
////        Intent intent=new Intent(context, Problem_Detail_Act.class);
////        intent.putExtra(Ctrl_Page.INTENT_KEY_REPORT_TYPE_ID, categoryModel.REPORT_TYPE_ID);
////        intent.putExtra(Ctrl_Page.INTENT_KEY_REPORT_ID, userReportModel.ID);
////        intent.putExtra(Ctrl_Page.INTENT_KEY_CLOSING_ID, alert.REFERENCE_ID);
////        intent.putExtra(Ctrl_Page.INTENT_KEY_OPEN_DIALOG, true);
////        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////        PendingIntent in = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
////        mBuilder.setContentIntent(in);
//
//        Table_Scheduled schedule= RealmMainHelper.getRealm().where(Table_Scheduled.class).equalTo(Table_Scheduled.PRIMARY_KEY, schId).findFirst();
//        if(schedule != null){
//            String recipient = "";
//            for(int a=0; a<schedule.getRecipients().size(); a++){
//
//            }
//            mBuilder.setContentText("Message has been sent to : " + );
//        }
//        mNotificationManager.notify(0, mBuilder.build());
//    }

    public static void generateAndroidNotif(Context context,String text){
        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setAutoCancel(true);
        mBuilder.setContentTitle(context.getString(R.string.app_name)).setSmallIcon(R.drawable.ic_launcher);
        SoundFunction.playNotif(context, mBuilder);
        mBuilder.setContentText(text);
        mNotificationManager.notify(0, mBuilder.build());
    }

    public static void clearAllOSNotif(Context context)
    {
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancelAll();
    }
}
