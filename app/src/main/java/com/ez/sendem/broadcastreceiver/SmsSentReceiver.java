package com.ez.sendem.broadcastreceiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.widget.Toast;

import com.ez.sendem.db.DBConstraint;
import com.ez.sendem.function.GeneralFunction;
import com.ez.sendem.services.MyService;

public class SmsSentReceiver extends BroadcastReceiver {
    public static int count = 0;
    @Override
    public void onReceive(Context context, Intent arg1) {
        switch (getResultCode()) {
            case Activity.RESULT_OK:
                count++;
                Toast.makeText(context, "SMS Sent " + count, Toast.LENGTH_SHORT).show();
                GeneralFunction.changeHistorySentStatus(DBConstraint.SCHEDULE_SEND_STATUS.SENT);
                break;
            case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                count++;
                Toast.makeText(context, "SMS generic failure " + count, Toast.LENGTH_SHORT).show();
                GeneralFunction.changeHistorySentStatus(DBConstraint.SCHEDULE_SEND_STATUS.FAILED);
                break;
            case SmsManager.RESULT_ERROR_NO_SERVICE:
                count++;
                Toast.makeText(context, "SMS no service " + count, Toast.LENGTH_SHORT).show();
                GeneralFunction.changeHistorySentStatus(DBConstraint.SCHEDULE_SEND_STATUS.FAILED);
                break;
            case SmsManager.RESULT_ERROR_NULL_PDU:
                count++;
                Toast.makeText(context, "SMS null PDU " + count, Toast.LENGTH_SHORT).show();
                GeneralFunction.changeHistorySentStatus(DBConstraint.SCHEDULE_SEND_STATUS.FAILED);
                break;
            case SmsManager.RESULT_ERROR_RADIO_OFF:
                count++;
                Toast.makeText(context, "SMS radio off " + count, Toast.LENGTH_SHORT).show();
                GeneralFunction.changeHistorySentStatus(DBConstraint.SCHEDULE_SEND_STATUS.FAILED);
                break;
        }
    }
}