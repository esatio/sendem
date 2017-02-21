package com.ez.sendem.ui;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.ez.sendem.R;
import com.ez.sendem.manager.PageManager;

import io.realm.Realm;
import io.realm.RealmChangeListener;

public class RootAct extends AppCompatActivity{

    protected static Activity act;
    protected static Class currClass;
    protected static int LAST_INDEX_ROOT_NAVBAR=-1;
    private FrameLayout lytContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        PageManager.setTheme(this);
        PageManager.setLanguage(this);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        super.onCreate(savedInstanceState);
        act = this;

        super.setContentView(R.layout.rootact);
        lytContent = (FrameLayout)findViewById(R.id.lytContent);
    }

    @Override
    public void setContentView(int layoutResID){
        getLayoutInflater().inflate(layoutResID, lytContent);
    }

    @Override
    public void setContentView(View view){
        lytContent.addView(view);
    }

    @Override
    public void onStart(){
        super.onStart();
        act = this;
        currClass = getClass();
    }

    @Override
    public void onResume(){
        super.onResume();
        act = this;
        currClass = getClass();
    }

    @Override
    public void onStop(){
        super.onStop();
//        if(currClass == getClass() && (currClass != Root_NavBar.class || Root_NavBar.CURRENT_INDEX == LAST_INDEX_ROOT_NAVBAR)){
//            act = null;
//            currClass = null;
//            LAST_INDEX_ROOT_NAVBAR = -1;
//        }else if(currClass == Root_NavBar.class){
//            LAST_INDEX_ROOT_NAVBAR = Root_NavBar.CURRENT_INDEX;
//        }else{
//            LAST_INDEX_ROOT_NAVBAR = -1;
//        }
//        RealmMainHelper.getRealm().removeChangeListener(dbListener);
    }

    public static Activity getActivity(){
        return act;
    }
}
