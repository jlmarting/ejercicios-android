package com.jlmarting.earthquakes.fragments;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jlmarting.earthquakes.fragments.abstracts.AbstractMapFragment;
import com.jlmarting.earthquakes.model.EarthQuake;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by javi-vaquero on 13/04/15.
 */
public class EarthQuakesMapDetailFragment extends AbstractMapFragment {

    private EarthQuake earthquake;
    @Override
    protected void getData() {
        String id =  getActivity().getIntent().getStringExtra(EarthQuakeListFragment.EQ_ID);
        earthquake = earthquakeDB.getById(id);
    }

    @Override
    protected void showMap() {

        MarkerOptions marker = createMarker(earthquake);
        map.addMarker(marker);

        CameraPosition camPos = new CameraPosition.Builder().target(marker.getPosition())
                .zoom(9)
                .build();
        CameraUpdate camUpd	= CameraUpdateFactory.newCameraPosition(camPos);
        map.animateCamera(camUpd);
    }



}
