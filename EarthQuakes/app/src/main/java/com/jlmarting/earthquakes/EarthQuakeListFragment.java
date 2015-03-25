package com.jlmarting.earthquakes;

import android.os.Bundle;
import android.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;


import com.jlmarting.earthquakes.adapter.EarthQuakeAdapter;
import com.jlmarting.earthquakes.model.EarthQuake;
import com.jlmarting.earthquakes.tasks.DownloadEarthQuakesTask;

import org.json.JSONObject;

import java.util.ArrayList;


public class EarthQuakeListFragment extends ListFragment implements DownloadEarthQuakesTask.AddEarthQuakeInterface{
    private JSONObject json;
    private ArrayList<EarthQuake> earthQuakes;
    private ArrayAdapter<EarthQuake> aa;

    /*
    Realmente no tenemos porque esperar a que la vista esté creada para realizar 
    la conexión, puesto que puede tardar
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        earthQuakes = new ArrayList<>();
        /*
        Debería dar fallo, dado que desde el thread no
        es posible acceder a la vista
        Ya no nos será util, usaremos el AsyncTask
        DownloadEarthQuakesTask que hemos creado

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                updateEarthQuakes();
            }
        });

       t.start();               */

        DownloadEarthQuakesTask task = new DownloadEarthQuakesTask(this);
        //los asynctask se ponen en marcha con execute
        task.execute(getString(R.string.earthquakes_url));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = super.onCreateView(inflater, container, savedInstanceState);
       // aa= new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,this.earthQuakes);
        aa= new EarthQuakeAdapter(getActivity(),R.layout.earthquake_layout,this.earthQuakes);
        setListAdapter(aa);
        return layout;
    }


    @Override
    public void addEarthQuake(EarthQuake earthQuake) {
        earthQuakes.add(0,earthQuake);
        aa.notifyDataSetChanged();
    }
}
