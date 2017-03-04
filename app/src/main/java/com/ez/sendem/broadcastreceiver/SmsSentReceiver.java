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
    @Override
    public void onReceive(Context context, Intent arg1) {
        switch (getResultCode()) {
            case Activity.RESULT_OK:
                Toast.makeText(context, "SMS Sent", Toast.LENGTH_SHORT).show();
                GeneralFunction.changeHistorySentStatus(DBConstraint.SCHEDULE_SEND_STATUS.SENT);
                break;
            case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                Toast.makeText(context, "SMS generic failure", Toast.LENGTH_SHORT).show();
                GeneralFunction.changeHistorySentStatus(DBConstraint.SCHEDULE_SEND_STATUS.FAILED);
                break;
            case SmsManager.RESULT_ERROR_NO_SERVICE:
                Toast.makeText(context, "SMS no service", Toast.LENGTH_SHORT).show();
                GeneralFunction.changeHistorySentStatus(DBConstraint.SCHEDULE_SEND_STATUS.FAILED);
                break;
            case SmsManager.RESULT_ERROR_NULL_PDU:
                Toast.makeText(context, "SMS null PDU", Toast.LENGTH_SHORT).show();
                GeneralFunction.changeHistorySentStatus(DBConstraint.SCHEDULE_SEND_STATUS.FAILED);
                break;
            case SmsManager.RESULT_ERROR_RADIO_OFF:
                Toast.makeText(context, "SMS radio off", Toast.LENGTH_SHORT).show();
                GeneralFunction.changeHistorySentStatus(DBConstraint.SCHEDULE_SEND_STATUS.FAILED);
                break;
        }
    }
}