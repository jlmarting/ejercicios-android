package com.jlmarting.earthquakes;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;


import com.jlmarting.earthquakes.R;
import com.jlmarting.earthquakes.fragments.SettingsFragment;
import com.jlmarting.earthquakes.managers.EarthQuakeAlarmManager;


public class SettingsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  addPreferencesFromResource(R.xml.userpreferences);
        getFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        pref.registerOnSharedPreferenceChangeListener(this);


    }


    // @Override
    public void onSharedPreferenceChanged(SharedPreferences pref, String key) {

        String Opcion1 = getString(R.string.pref_auto_update);
        String Opcion2 = getString(R.string.auto_update_interval);

        if (key.equals(Opcion1)) {
            if (pref.getBoolean(Opcion1, false)) {

                long interval = Long.parseLong(pref.getString(Opcion2, "68"));

                EarthQuakeAlarmManager.setAlarm(this, interval * 60 * 1000);
            } else {
                EarthQuakeAlarmManager.stopAlarm(this);
               }
            } else if (key.equals(Opcion2)) {

                long interval = Long.parseLong(pref.getString(Opcion2, "68"));
                EarthQuakeAlarmManager.updateAlarm(this, interval * 60 * 1000);

            }

        }
    }







