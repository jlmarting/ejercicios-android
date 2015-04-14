package com.jlmarting.earthquakes.abstracts;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jlmarting.earthquakes.R;
import com.jlmarting.earthquakes.database.EarthQuakeDB;
import com.jlmarting.earthquakes.model.EarthQuake;


public abstract class AbstractMapFragment extends MapFragment implements GoogleMap.OnMapLoadedCallback, GoogleMap.CancelableCallback {

    private final int maxZoomAfterAnimation = 9;

    protected EarthQuakeDB earthquakeDB;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        earthquakeDB = new EarthQuakeDB(getActivity());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout =  super.onCreateView(inflater, container, savedInstanceState);
        getMap().setOnMapLoadedCallback(this);
        return layout;
    }


    protected MarkerOptions createMarker(EarthQuake earthquake){
        LatLng point = new LatLng(earthquake.getCoords().getLat(),
                earthquake.getCoords().getLng());
        MarkerOptions marker = new MarkerOptions()
                .position(point)
                .title(earthquake.getPlace())
                .snippet(getString(R.string.magnitude, String.format("%.2f",earthquake.getMagnitude())));

        return marker;
    }

    @Override
    public void onMapLoaded() {
        this.getData();
        this.showMap();
    }

    @Override
    public void onFinish() {
        if (getMap().getCameraPosition().zoom > maxZoomAfterAnimation) {
            getMap().moveCamera(CameraUpdateFactory.zoomTo(maxZoomAfterAnimation));
        }
    }

    @Override
    public void onCancel() {

    }

    abstract protected void getData();
    abstract protected void showMap();
}
