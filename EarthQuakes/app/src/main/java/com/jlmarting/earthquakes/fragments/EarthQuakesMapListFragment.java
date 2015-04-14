package com.jlmarting.earthquakes.fragments;

/**
 * Created by cursomovil on 14/04/15.
 */


        import android.content.SharedPreferences;
        import android.os.Bundle;
        import android.preference.PreferenceManager;

        import com.google.android.gms.maps.CameraUpdate;
        import com.google.android.gms.maps.CameraUpdateFactory;
        import com.google.android.gms.maps.GoogleMap;
        import com.google.android.gms.maps.model.LatLngBounds;
        import com.google.android.gms.maps.model.MarkerOptions;
        import com.jlmarting.earthquakes.R;
        import com.jlmarting.earthquakes.abstracts.AbstractMapFragment;
        import com.jlmarting.earthquakes.model.EarthQuake;

        import java.util.List;


public class EarthQuakesMapListFragment extends AbstractMapFragment {

    private SharedPreferences prefs;
    private List<EarthQuake> earthquakes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
    }

    @Override
    protected void getData() {
        double magnitude = Double.parseDouble(prefs.getString(getString(R.string.min_magnitude), "0.0"));
        earthquakes =  earthquakeDB.getAllByMagnitude(magnitude);
    }

    @Override
    protected void showMap() {

        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        getMap().setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        for (EarthQuake earthquake: earthquakes) {
            MarkerOptions marker = createMarker(earthquake);
            getMap().addMarker(marker);
            builder.include(marker.getPosition());

        }

        LatLngBounds bounds = builder.build();

        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 45);
        getMap().animateCamera(cu);
    }

}