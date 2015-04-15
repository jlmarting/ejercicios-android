package com.jlmarting.earthquakes.fragments;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jlmarting.earthquakes.R;


public class SettingsFragment extends PreferenceFragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.userpreferences);


    }



}
