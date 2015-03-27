package com.jlmarting.earthquakes;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.ArrayMap;
import android.util.Log;

import com.jlmarting.earthquakes.tasks.DownloadEarthQuakesTask;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by cursomovil on 26/03/15.
 */
public class SettingsActivity extends PreferenceActivity       {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //addPreferencesFromResource(R.xml.userpreferences);
        /*
        El método anterior es obsoleto, así que recurrimos a la nueva propuesta
        que es definir las preferencias por medio de fragmentos.
        Introducimos el nuevo fragmento SettingsFragment
        en la actividad.
        La opción que usábamos habitualmente era diferente,
        recurríamos directamente a XML.
         */
        getFragmentManager()
                .beginTransaction()
                    .replace(android.R.id.content, new SettingsFragment())
                .commit();

    }



}
