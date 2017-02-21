package com.ez.sendem.ui;

import android.os.Bundle;

import com.ez.sendem.R;
import com.ez.sendem.manager.PageManager;

import java.util.Timer;
import java.util.TimerTask;

public class LoadingAct extends RootAct {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loadingact);
    }

    @Override
    public void onResume(){
        super.onResume();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                PageManager.open_FirstPage(LoadingAct.this);
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 3000);
    }
}
