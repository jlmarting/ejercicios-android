package com.jlmarting.earthquakes.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jlmarting.earthquakes.abstracts.AbstractMapFragment;
import com.jlmarting.earthquakes.model.EarthQuake;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class EarthQuakesMapsFragment extends AbstractMapFragment implements GoogleMap.OnMapLoadedCallback {


    private EarthQuake earthQuake;
    private TextView lblid;
    private TextView lblsite;
    private TextView lblurl;

    private MarkerOptions marker;
    private CameraUpdate camUpd;
    private Double Lng;
    private Double Lat;
    private List<EarthQuake> earthQuakes;

    public GoogleMap mapa = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout= super.onCreateView(inflater, container, savedInstanceState);
        mapa=getMap();
        mapa.setOnMapLoadedCallback(this);
        return layout;
    }

    public void setEarthQuakes(List<EarthQuake> earthquakes) {
        this.earthQuakes = earthquakes;
    }

    @Override
    public void onMapLoaded() {
            LatLngBounds.Builder builder= new LatLngBounds.Builder();
                for (int i = 0; i < earthQuakes.size(); i++) {

                    LatLng earthquakePos = new LatLng(earthQuakes.get(i).getCoords().getLng(),
                            earthQuakes.get(i).getCoords().getLat());
                    String Place = earthQuakes.get(i).getPlace();
                    String Url = earthQuakes.get(i).getUrl();
                    Double Magnitude = earthQuakes.get(i).getMagnitude();

                    mapa.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                    MarkerOptions marker =
                            new MarkerOptions()
                                    .position(earthquakePos)
                                    .title(Place)
                                    .snippet(String.valueOf(Magnitude));

                    mapa.addMarker(marker);
                    builder.include(earthquakePos);


                }

                LatLngBounds bounds = builder.build();

            if(earthQuakes.size()==1){
                LatLng earthquakePosition = new LatLng(earthQuakes.get(0).getCoords().getLng(),
                        earthQuakes.get(0).getCoords().getLat());
                CameraPosition cp = new CameraPosition.Builder()
                        .target(earthquakePosition)
                        .zoom(5)
                        .build();

                camUpd = CameraUpdateFactory.newCameraPosition(cp);
        }
        else {
                camUpd = CameraUpdateFactory.newLatLngBounds(bounds, 0);
        }

                mapa.animateCamera(camUpd);

            }

    @Override
    protected void getData() {

    }

    @Override
    protected void showMap() {

    }


}
