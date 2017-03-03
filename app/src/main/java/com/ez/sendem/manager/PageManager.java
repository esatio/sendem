package com.ez.sendem.manager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.ez.sendem.R;
import com.ez.sendem.constraint.AppConstraint;
import com.ez.sendem.ui.HistoryFragment;
import com.ez.sendem.ui.InactiveFragment;
import com.ez.sendem.ui.LoadingAct;
import com.ez.sendem.ui.RootNavBar;
import com.ez.sendem.ui.ScheduleNewAct;
import com.ez.sendem.ui.ScheduledFragment;

import java.util.Locale;

public class PageManager {

    /*
    comment :
        1. set theme berguna untuk set tema yang sedang digunakan
        2. tema disimpan di "SharedPreferences". "SharedPreferences" ini merupakan cache di android
    */
    public static void setTheme(Activity act)
    {
        /*
        comment :
            1. get theme dari shared preferences
        */
        String theme = SharedPrefAccess.getFromXML(act, SharedPrefAccess.SHARED_PREF_USERDATA, SharedPrefAccess.KEY_THEME);
        if(theme.equalsIgnoreCase(""))
        {
            SharedPrefAccess.saveToXML(act, SharedPrefAccess.SHARED_PREF_USERDATA, SharedPrefAccess.KEY_THEME, AppConstraint.THEME_TYPE.THEME_MEDIUM);
            /*
            comment :
                1. cara set theme di android
            */
            act.setTheme(R.style.themeDefaultMedium);
        }
        else
        {
            if(theme.equalsIgnoreCase(AppConstraint.THEME_TYPE.THEME_LARGE))
                act.setTheme(R.style.themeDefaultLarge);
            else if(theme.equalsIgnoreCase(AppConstraint.THEME_TYPE.THEME_MEDIUM))
                act.setTheme(R.style.themeDefaultMedium);
            else if(theme.equalsIgnoreCase(AppConstraint.THEME_TYPE.THEME_SMALL))
                act.setTheme(R.style.themeDefaultSmall);
        }
    }

    /*
    comment :
        1. set language berguna untuk set language yang sedang digunakan
        2. language disimpan di "SharedPreferences". "SharedPreferences" ini merupakan cache di android
    */
    public static void setLanguage(Activity act){
        String lang = SharedPrefAccess.getFromXML(act, SharedPrefAccess.SHARED_PREF_USERDATA, SharedPrefAccess.KEY_LANG);
        if(lang.equalsIgnoreCase(""))
        {
            SharedPrefAccess.saveToXML(act, SharedPrefAccess.SHARED_PREF_USERDATA, SharedPrefAccess.KEY_LANG, AppConstraint.LANG_TYPE.LANG_IN);
            lang = AppConstraint.LANG_TYPE.LANG_IN;
        }

        /*
        comment :
            1. cara set language di android adalah dengan Locale.
        */
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        act.getResources().updateConfiguration(config, act.getResources().getDisplayMetrics());
    }

    public static void open_FirstPage(Context context){
        /*
        comment :
            1. cara pindah activity di android adalah dengan:
                    Intent intent = new Intent(context, (nama class));
                    context.startActivity(intent);
            2. setFlags berguna untuk clear semua flag default dan menambahkan flag di bagian paramnya
            3. addFlags berguna untuk add flag
            4. FLAG_ACTIVITY_CLEAR_TASK dan FLAG_ACTIVITY_NEW_TASK berguna untuk clear history sebelumnya.
                (agar back tidak kembali ke halaman sebelumnya)
        */
        Intent intent = new Intent(context, RootNavBar.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void open_LoadingPage(Context context){
        Intent intent = new Intent(context, LoadingAct.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void open_ScheduledAct(FragmentActivity act){
        FragmentManager fragMan = act.getSupportFragmentManager();
        FragmentTransaction fragTransaction = fragMan.beginTransaction();

        Fragment myFrag = ScheduledFragment.newInstance();
        fragTransaction.replace(R.id.content, myFrag , "fragment scheduled");
        fragTransaction.commit();
    }

    public static void open_InactiveAct(FragmentActivity act){
        FragmentManager fragMan = act.getSupportFragmentManager();
        FragmentTransaction fragTransaction = fragMan.beginTransaction();

        Fragment myFrag = InactiveFragment.newInstance();
        fragTransaction.replace(R.id.content, myFrag , "fragment scheduled");
        fragTransaction.commit();
    }

    public static void open_HistoryAct(FragmentActivity act){
        FragmentManager fragMan = act.getSupportFragmentManager();
        FragmentTransaction fragTransaction = fragMan.beginTransaction();

        Fragment myFrag = HistoryFragment.newInstance();
        fragTransaction.replace(R.id.content, myFrag , "fragment scheduled");
        fragTransaction.commit();
    }

    public static void open_ScheduleNew(Context context){
        Intent intent = new Intent(context, ScheduleNewAct.class);
        context.startActivity(intent);
    }

    public static void exit_App(Activity act){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        act.startActivity(intent);
        System.exit(0);
    }

    public static void restartApp(Activity act){
        act.finish();
        open_LoadingPage(act);
    }
}
