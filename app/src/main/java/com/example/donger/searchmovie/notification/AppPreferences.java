package com.example.donger.searchmovie.notification;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AppPreferences {
    private static final String PREFERENCES_DAILY = "preferences_daily";
    private static final String PREFERENCES_DAILY_TIME = "preferences_daily_time";
    private static final String PREFERENCES_RELEASE = "preferences_release";
    private static final String PREFERENCES_RELEASE_TIME = "preferences_release_time";
    SharedPreferences sharedPreferences;
    Context context;

    public AppPreferences(Context context) {
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        this.context = context;
    }

    public void setDailyReminder(Boolean input, String time){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(PREFERENCES_DAILY, input);
        editor.putString(PREFERENCES_DAILY_TIME, time);
        editor.apply();
    }

    public Boolean getDailyReminder(){
        return sharedPreferences.getBoolean(PREFERENCES_DAILY, false);
    }

    public void setReleaseReminder(Boolean input, String time){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(PREFERENCES_RELEASE, input);
        editor.putString(PREFERENCES_RELEASE_TIME, time);
        editor.apply();
    }

    public Boolean getReleaseReminder(){
        return sharedPreferences.getBoolean(PREFERENCES_RELEASE, false);
    }
}
