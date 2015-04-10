package com.jlmarting.earthquakes;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jlmarting.earthquakes.database.EarthQuakeDB;
import com.jlmarting.earthquakes.fragments.EarthQuakeListFragment;
import com.jlmarting.earthquakes.fragments.EarthQuakesMapsFragment;
import com.jlmarting.earthquakes.model.EarthQuake;

import java.util.ArrayList;
import java.util.List;

public class EarthQuakeActivityDetail extends ActionBarActivity {

    private EarthQuake earthQuake;
    private TextView lblid;
    private TextView lblsite;
    private TextView lblurl;
    private GoogleMap mapa ;
    private MarkerOptions marker;
    private CameraPosition camPos;
    private CameraUpdate camUpd;
    private Double Lng;
    private Double Lat;
    private ArrayList<EarthQuake> earthQuakes;
    private EarthQuakesMapsFragment mapfragment;

    private EarthQuakeDB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(com.jlmarting.earthquakes.R.layout.activity_earth_quake_activity_detail);
        db = new EarthQuakeDB(this);


        lblid= (TextView) findViewById(com.jlmarting.earthquakes.R.id.lblid);
        lblsite= (TextView) findViewById(com.jlmarting.earthquakes.R.id.lblsite);
        lblurl= (TextView) findViewById(com.jlmarting.earthquakes.R.id.lblurl);
        Intent detailIntent = getIntent();
       // Log.d("CHANGE", String.valueOf(lblid));
      //  earthQuake= detailIntent.getParcelableExtra(EarthQuakeListFragment.EARTHQUAKES_ITEM);
        //earthQuake= detailIntent.getExtras(EarthQuakeListFragment.EARTHQUAKES_ITEM);

        String id = detailIntent.getStringExtra(EarthQuakeListFragment.EARTHQUAKES_ITEM);
        earthQuake = db.getById(id);

        //Log.d("change", earthQuake.get_id());
        populateView(earthQuake);
        mapfragment= (EarthQuakesMapsFragment)getFragmentManager().findFragmentById(R.id.mapa);
       //no se puede1 hacer esto porque lo hace android
       // MapsFragment mapfragment = new MapsFragment();
        List <EarthQuake> earthQuakes = new ArrayList<>();
        earthQuakes.add(earthQuake);
        mapfragment.setEarthQuakes(earthQuakes);

    }

    private void populateView(EarthQuake earthquake){

        lblid.setText(earthQuake.get_id());
        lblsite.setText(earthQuake.getPlace());
        lblurl.setText(earthQuake.getUrl());
       // lblurl.setText(Html.fromHtml("<a href=" + earthQuake.getUrl() + "></a>"));

    }
}
