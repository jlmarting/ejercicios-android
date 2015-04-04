package com.jlmarting.earthquakes;


import android.app.AlarmManager;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Introducimos el listener de settings en este fragmento, no en la actividad que lo usa
 * para no centralizar en él toda la gestión
 */
public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {


    private static final int ALARM_EARTHQUAKES = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        prefs.registerOnSharedPreferenceChangeListener(this);
        addPreferencesFromResource(R.xml.userpreferences);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.d("PREFS_CHANGED", key);
        Log.d("ALL_PREFERENCES", sharedPreferences.getAll().toString());
        //UPDATE_INTERVAL
        Map<String, ?> settings = (Map) sharedPreferences.getAll();

        //No se puede hacer un switch de strings...

        String PREF_AUTO_UPDATE = getString(R.string.PREF_AUTO_UPDATE);
        String PREF_UPDATE_INTERVAL = getString(R.string.PREF_UPDATE_INTERVAL);
        String PREF_MIN_MAG = getString(R.string.PREF_MIN_MAG);
        /*
        Tenemos que tener en cuenta que Settings es accesible globalmente por toda la app.
        Por lo tanto no nos tenemos que preocupar de pasar valores.
        En la actividad o fragmento (como en este caso) en onResume puede ser buena idea
        para leer estos parámetros
         */

        if (key.equals(PREF_AUTO_UPDATE)) {

        } else if (key.equals(PREF_UPDATE_INTERVAL)) {
            AlarmManager alarmManager =
                    (AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);

            int alarmType = AlarmManager.ELAPSED_REALTIME;
            String ALARM_ACTION = "ALARM_ACTION";
            Intent intentToFire = new Intent(ALARM_ACTION);
           // PendingIntent alarmIntent = PendingIntent.getService(getActivity(),ALARM_EARTHQUAKES,false);
            //alarmManager.set(alarmType,100,intentToFire);

        } else if (key.equals(PREF_AUTO_UPDATE)) {
            double minMag = Double.parseDouble(sharedPreferences.getString(key, "0"));
        }


        /*
        switch (key) {
            case "UPDATE_INTERVAL": Log.d("=>"+key.toString(), settings.get(key).toString());break;
            default: Log.d("=>"+key.toString(), settings.get(key).toString() );
        }
        */


    }
}
