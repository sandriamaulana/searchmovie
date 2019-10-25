package com.example.donger.searchmovie.notification;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.donger.searchmovie.R;

public class SettingActivity extends AppCompatActivity {

    private DailyReminder dailyReminder;
    private ReleaseRemainder releaseReminder;
    private AppPreferences appPreferences;

    String dailyMessage;
    String dailyTime = "7:00";
    String releaseTime = "8:00";

    Switch sbDaily;
    Switch sbRelease;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setTitle(R.string.setting);

        sbDaily =(Switch) findViewById(R.id.sb_daily);
        sbRelease = (Switch)findViewById(R.id.sb_release);

        dailyReminder = new DailyReminder();
        releaseReminder = new ReleaseRemainder();

        appPreferences = new AppPreferences(this);
        loadPreference();
        sbDaily.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                appPreferences.setDailyReminder(isChecked, dailyTime);
                if (isChecked) {
                    dailyReminder.setRepeatingAlarm(SettingActivity.this, DailyReminder.DAILY_REMINDER,
                            dailyTime, dailyMessage = getResources().getString(R.string.daily_reminder));
                } else {
                    dailyReminder.cancelAlarm(SettingActivity.this);
                }
            }
        });

        sbRelease.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                appPreferences.setReleaseReminder(isChecked, releaseTime);
                if (isChecked) {
                    releaseReminder.setRepeatingAlarm(SettingActivity.this, releaseTime);
                } else {
                    releaseReminder.cancelAlarm(SettingActivity.this);
                }
            }
        });

    }

    private void loadPreference() {
        sbDaily.setChecked(appPreferences.getDailyReminder());
        sbRelease.setChecked(appPreferences.getReleaseReminder());
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }


}
