package com.ez.sendem.function;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.widget.Toast;

import com.ez.sendem.broadcastreceiver.SMSDeliveredReceiver;
import com.ez.sendem.broadcastreceiver.SmsSentReceiver;

import java.util.ArrayList;

public class SMSFunction {

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
//            sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
            Toast.makeText(mContext, "SMS sent",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(mContext, "SMS sending failed...",Toast.LENGTH_SHORT).show();
        }

    }
}
