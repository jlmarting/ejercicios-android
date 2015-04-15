package com.jlmarting.earthquakes.fragments;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jlmarting.earthquakes.abstracts.AbstractMapFragment;
import com.jlmarting.earthquakes.model.EarthQuake;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jlmarting on 15/04/15.
 */
    public class EarthQuakesMapDetailFragment extends AbstractMapFragment {

        private EarthQuake earthquake;
        @Override
        protected void getData() {
            String id =  getActivity().getIntent().getStringExtra(EarthQuakeListFragment.EARTHQUAKES_ITEM);
            earthquake = earthquakeDB.getById(id);
        }

        @Override
        protected void showMap() {

            MarkerOptions marker = createMarker(earthquake);
            this.getMap().addMarker(marker);

            CameraPosition camPos = new CameraPosition.Builder().target(marker.getPosition())
                    .zoom(5)
                    .build();
            CameraUpdate cameraUpdate	= CameraUpdateFactory.newCameraPosition(camPos);
            this.getMap().animateCamera(cameraUpdate);
        }



    }
