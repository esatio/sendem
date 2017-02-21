package com.ez.sendem.manager;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefAccess {
    public static final String SHARED_PREF_USERDATA = "UserData";

    public static final String KEY_THEME = "Theme";
    public static final String KEY_LANG = "Key1";

    public static void deleteFromXML(Context context, String sharedPrefName, String key)
    {
        SharedPreferences preferences = context.getSharedPreferences(sharedPrefName,Context.MODE_PRIVATE);
        preferences.edit().remove(key).commit();
    }

    public static void saveToXML(Context context, String sharedPrefName, String key, String value)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(sharedPrefName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getFromXML(Context context, String sharedPrefName, String key)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(sharedPrefName,Context.MODE_PRIVATE);
        String value = sharedPreferences.getString(key, "");
        return value;
    }

    public static void clearAllSharedPref(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_USERDATA,Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();
    }
}

