package com.ez.sendem.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ez.sendem.function.ServiceFunction;

public class RebootBroadcastReceiver extends BroadcastReceiver
{
    public void onReceive(Context arg0, Intent arg1)
    {
        ServiceFunction.StartMyService(arg0);
    }
}