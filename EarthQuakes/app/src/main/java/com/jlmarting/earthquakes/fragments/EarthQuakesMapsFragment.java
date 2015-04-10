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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jlmarting.earthquakes.model.EarthQuake;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class EarthQuakesMapsFragment extends MapFragment implements GoogleMap.OnMapLoadedCallback {


    private EarthQuake earthQuake;
    private TextView lblid;
    private TextView lblsite;
    private TextView lblurl;
    private GoogleMap mapa =null;
    private MarkerOptions marker;
    private CameraUpdate camUpd;
    private Double Lng;
    private Double Lat;
    private List<EarthQuake> earthQuakes;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

           View layout= super.onCreateView(inflater, container, savedInstanceState);
        mapa=getMap();
        mapa.setOnMapLoadedCallback(this);
        return layout;

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void setEarthQuakes(List<EarthQuake> earthquake) {

        this.earthQuakes = earthquake;

       // View layout




    }


    @Override
    public void onMapLoaded() {

            LatLngBounds.Builder builder= new LatLngBounds.Builder();

                for (int i = 0; i < earthQuakes.size(); i++) {

                    LatLng eartqueakeposition = new LatLng(earthQuakes.get(i).getCoords().getLng(),
                            earthQuakes.get(i).getCoords().getLat());
                    Log.d("jaione", String.valueOf(eartqueakeposition));
                    String Place = earthQuakes.get(i).getPlace();
                    String Url = earthQuakes.get(i).getUrl();
                    Double Magnitude = earthQuakes.get(i).getMagnitude();

                    mapa.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                    MarkerOptions marker = new MarkerOptions().position(eartqueakeposition).title(Place).snippet(String.valueOf(Magnitude));

                    mapa.addMarker(marker);
                    builder.include(marker.getPosition());


                }

                LatLngBounds bounds = builder.build();

                // LatLng position = new LatLng(Lng, Lat);
                // CameraPosition camPos = new CameraPosition.Builder().target(position)


            if(earthQuakes.size()==1){
                camUpd = CameraUpdateFactory.newLatLngZoom(new LatLng(earthQuakes.get(0).getCoords().getLat(),
                        earthQuakes.get(0).getCoords().getLng()), 5);
        }
        else {
                camUpd = CameraUpdateFactory.newLatLngBounds(bounds, 0);
        }

                // .zoom(5)

                // .bearing(45)

                //   .tilt(70)

                // .build();

                //   camUpd = CameraUpdateFactory.newCameraPosition(camPos);

                //   mapa.animateCamera(camUpd);

                mapa.animateCamera(camUpd);

            }



}
