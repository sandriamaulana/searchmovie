package com.example.donger.searchmovie;


import android.app.NotificationManager;
import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.donger.searchmovie.db.FavoriteHelper;
import com.example.donger.searchmovie.notification.DailyReminder;
import com.example.donger.searchmovie.notification.ReleaseRemainder;
import com.example.donger.searchmovie.notification.SettingActivity;



public class MainActivity extends AppCompatActivity {
    public static final String KEY_FRAGMENT = "fragment";
    private Fragment selected = new NowPlayingFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView btmNav = findViewById(R.id.bnv_movies);
        btmNav.setOnNavigationItemSelectedListener(bnvListener);

        FavoriteHelper favoriteHelper = FavoriteHelper.getInstance(getApplicationContext());
        favoriteHelper.open();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_container, selected).commit();

        } else {
            selected = getSupportFragmentManager().getFragment(savedInstanceState, KEY_FRAGMENT);

            getSupportFragmentManager().beginTransaction().replace(R.id.fl_container, selected).commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.favorite_list:
                FavoriteFragment favoriteFragment = new FavoriteFragment();
                FragmentManager mFragmentManager = getSupportFragmentManager();
                FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.fl_container,favoriteFragment);
                mFragmentTransaction.addToBackStack(null);
                mFragmentTransaction.commit();
                selected = new FavoriteFragment();
                BottomNavigationView btmNav = findViewById(R.id.bnv_movies);
                btmNav.getMenu().getItem(0).setCheckable(false);
                break;
            case R.id.action_language:
                Intent intentLanguage = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(intentLanguage);
                break;
            case R.id.action_setting:
                Intent intentRelease = new Intent(this, SettingActivity.class);
                startActivity(intentRelease);
                break;
            default:
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_container,selected).commit();
        return true;

    }

    private BottomNavigationView.OnNavigationItemSelectedListener bnvListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            BottomNavigationView btmNav = findViewById(R.id.bnv_movies);
            btmNav.getMenu().getItem(0).setCheckable(true);
            switch (menuItem.getItemId()){
                case R.id.now_playing_nav:
                    selected = new NowPlayingFragment();
                    break;
                case R.id.up_coming_nav:
                    selected = new UpComingFragment();
                    break;
                case R.id.search_nav:
                    selected = new SearchFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_container, selected).commit();
            return true;
        }
    };
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        getSupportFragmentManager().putFragment(outState, KEY_FRAGMENT, selected);
        super.onSaveInstanceState(outState);
    }

}
