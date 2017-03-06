package com.ez.sendem.services;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;

import com.ez.sendem.constraint.AppConstraint;
import com.ez.sendem.function.ServiceFunction;

public class MyJobServices extends JobService{

    @Override
    public boolean onStartJob(JobParameters params) {
        if(AppConstraint.ServiceIntent==null || !ServiceFunction.isMyServiceRunning(this)) {
            AppConstraint.ServiceIntent=new Intent(this,MyService.class);
            startService(AppConstraint.ServiceIntent);
        }

        return false; // true if we're not done yet
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        // true if we'd like to be rescheduled
        return true;
    }

}
