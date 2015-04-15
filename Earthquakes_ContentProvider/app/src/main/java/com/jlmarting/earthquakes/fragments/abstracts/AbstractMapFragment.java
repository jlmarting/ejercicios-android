package com.jlmarting.earthquakes.fragments.abstracts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jlmarting.earthquakes.R;
import com.jlmarting.earthquakes.providers.EarthQuakeDB;
import com.jlmarting.earthquakes.model.EarthQuake;

/**
 * Created by javi-vaquero on 13/04/15.
 */
public abstract class AbstractMapFragment extends MapFragment implements GoogleMap.OnMapLoadedCallback, GoogleMap.CancelableCallback {

    private final int maxZoomAfterAnimation = 9;


    protected EarthQuakeDB earthquakeDB;
    protected GoogleMap map;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        earthquakeDB = new EarthQuakeDB(getActivity());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout =  super.onCreateView(inflater, container, savedInstanceState);
        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        map.setOnMapLoadedCallback(this);
    }

    private void setUpMapIfNeeded(){
        if (map == null){
            map = getMap();
        }
    }



    protected MarkerOptions createMarker(EarthQuake earthquake){
        LatLng point = new LatLng(earthquake.getCoords().getLatitude(),
                earthquake.getCoords().getLongitude());
        MarkerOptions marker = new MarkerOptions()
                .position(point)
                .title(earthquake.getPlace())
                .snippet(getString(R.string.magnitude, String.format("%.2f",earthquake.getMagnitude())));

        return marker;

    }

    @Override
    public void onMapLoaded() {
        getData();
        showMap();
    }

    @Override
    public void onFinish() {
        if (map.getCameraPosition().zoom > maxZoomAfterAnimation) {
            map.moveCamera(CameraUpdateFactory.zoomTo(maxZoomAfterAnimation));
        }
    }
    @Override
    public void onCancel() {

    }

    abstract protected void getData();
    abstract protected void showMap();
}
