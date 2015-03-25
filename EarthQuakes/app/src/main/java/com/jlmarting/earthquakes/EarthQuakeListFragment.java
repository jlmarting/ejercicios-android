package com.jlmarting.earthquakes;

import android.app.Activity;
import android.os.Bundle;
import android.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.jlmarting.earthquakes.dummy.DummyContent;
import com.jlmarting.earthquakes.model.Coordinate;
import com.jlmarting.earthquakes.model.EarthQuake;
import com.jlmarting.earthquakes.tasks.DownloadEarthQuakesTask;

import org.apache.http.HttpConnection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;


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
        aa= new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,this.earthQuakes);
        setListAdapter(aa);
        return layout;
    }


    @Override
    public void addEarthQuake(EarthQuake earthQuake) {
        earthQuakes.add(0,earthQuake);
        aa.notifyDataSetChanged();
    }
}
