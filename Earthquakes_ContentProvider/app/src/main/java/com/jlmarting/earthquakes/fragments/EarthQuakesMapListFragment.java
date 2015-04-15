package com.jlmarting.earthquakes.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jlmarting.earthquakes.R;
import com.jlmarting.earthquakes.fragments.abstracts.AbstractMapFragment;
import com.jlmarting.earthquakes.model.EarthQuake;
import com.jlmarting.earthquakes.services.DownloadEarthQuakesService;

import java.util.List;

/**
 * Created by javi-vaquero on 13/04/15.
 */
public class EarthQuakesMapListFragment extends AbstractMapFragment{

    private SharedPreferences prefs;
    private List<EarthQuake> earthquakes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

    }

    @Override
    protected void getData() {
        double magnitude = Double.parseDouble(prefs.getString(getString(R.string.PREF_MIN_MAGNITUDE), "0.0"));
        earthquakes =  earthquakeDB.getAllByMagnitude(magnitude);
    }

    @Override
    protected void showMap() {

        map.clear();

        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        for (EarthQuake earthquake: earthquakes) {
            MarkerOptions marker = createMarker(earthquake);
            map.addMarker(marker);
            builder.include(marker.getPosition());

        }

        LatLngBounds bounds = builder.build();

        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 45);
        map.animateCamera(cu);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_refresh,menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id==R.id.action_refresh){
            Intent download = new Intent(getActivity(), DownloadEarthQuakesService.class);
            getActivity().startService(download);
        }
        return super.onOptionsItemSelected(item);
    }

}
