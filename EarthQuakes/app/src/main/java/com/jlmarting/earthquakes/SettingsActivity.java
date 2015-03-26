package com.jlmarting.earthquakes;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by cursomovil on 26/03/15.
 */
public class SettingsActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.screen_layout);
    }
}
