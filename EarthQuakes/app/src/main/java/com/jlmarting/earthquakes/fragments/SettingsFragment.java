package com.jlmarting.earthquakes.fragments;


import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.jlmarting.earthquakes.R;

public class SettingsFragment extends PreferenceFragment  {

    private String EARTHQUAKE = "EARTHQUAKE";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.userpreferences);

    //  SharedPreferences  prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        //prefs.registerOnSharedPreferenceChangeListener(this);
    }

   /* @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {



        String Opcion1=getString(R.string.pref_auto_update);
        String Opcion2=getString(R.string.auto_update_interval);
        String Opcion3=getString(R.string.magnitude);


        if (key.equals(Opcion1)){
            //Auto refresh
            Log.d(EARTHQUAKE, "Hemos cambiado: " + key + key + " => " + sharedPreferences.getBoolean(getString(R.string.pref_auto_update), true));
        } else if(key.equals(Opcion2)){
            //Tiempo de refresco
            Log.d(EARTHQUAKE, "Hemos cambiado: " + key + key + " => " + sharedPreferences.getString(getString(R.string.auto_update_interval), ""));
        } else if(key.equals(Opcion3)){
            //Filtrar por magnitud, no hay que hacer nada, se guarda solo
            Log.d(EARTHQUAKE, "Hemos cambiado: " + key +" => "+  sharedPreferences.getString( getString(R.string.magnitude),""));


        }
    }*/



}


