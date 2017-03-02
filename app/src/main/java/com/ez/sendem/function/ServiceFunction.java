package com.ez.sendem.function;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.ez.sendem.constraint.AppConstraint;
import com.ez.sendem.services.MyJobServices;
import com.ez.sendem.services.MyService;

public class ServiceFunction {
    public static JobScheduler jobScheduler;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void StartMyService(Context context){
        //17 ga jalan
        //19 jalan
        if(GeneralFunction.getDeviceApiLevel()<21){
            if(AppConstraint.ServiceIntent==null || !ServiceFunction.isMyServiceRunning(context)) {
                AppConstraint.ServiceIntent=new Intent(context,MyService.class);
                context.startService(AppConstraint.ServiceIntent);
            }
        } else {
            if(jobScheduler==null){
                ComponentName serviceComponent = new ComponentName(context, MyJobServices.class);
                JobInfo.Builder builder = new JobInfo.Builder(0, serviceComponent);
                builder.setMinimumLatency(1 * 1000); // wait at least
                builder.setOverrideDeadline(50 * 1000); // maximum delay
                builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
                builder.setRequiresDeviceIdle(false);
                builder.setRequiresCharging(false); // we don't care if the device is charging or not
                jobScheduler = (JobScheduler)context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
                jobScheduler.schedule(builder.build());
            }
        }
    }

    public static void StopMyService(Context context) {
        if(AppConstraint.ServiceIntent!=null){
            context.stopService(AppConstraint.ServiceIntent);
        }
    }

    public static boolean isMyServiceRunning(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (MyService.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
