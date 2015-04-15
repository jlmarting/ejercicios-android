package com.jlmarting.earthquakes;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;

import com.jlmarting.earthquakes.fragments.SettingsFragment;
import com.jlmarting.earthquakes.managers.EarthQuakeAlarmManager;
import com.jlmarting.earthquakes.services.DownloadEarthQuakesService;


public class SettingsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //deprecated
        //addPreferencesFromResource(R.xml.userpreferences);

        //Display the fragment as the main content
        getFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals(getString(R.string.PREF_AUTO_UPDATE))){
            // Start/Stop auto updates
            if(sharedPreferences.getBoolean(key, true)){
                long interval = Long.parseLong(sharedPreferences.getString(getString(R.string.PREF_UPDATE_INTERVAL), "1"));
                EarthQuakeAlarmManager.setAlarm(this, interval * 60 *1000);

            }
            else{
                EarthQuakeAlarmManager.cancelAlarm(this);
            }

        }
        else if(key.equals(getString(R.string.PREF_UPDATE_INTERVAL))){
            // Change auto refresh interval
            long interval = Long.parseLong(sharedPreferences.getString(getString(R.string.PREF_UPDATE_INTERVAL), "1"));
            EarthQuakeAlarmManager.updateAlarm(this, interval * 60 *1000);

        }
    }


}
