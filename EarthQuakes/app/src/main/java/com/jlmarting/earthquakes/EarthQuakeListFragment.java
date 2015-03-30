package com.jlmarting.earthquakes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.ListFragment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import com.jlmarting.earthquakes.adapter.EarthQuakeAdapter;
import com.jlmarting.earthquakes.database.EarthQuakeDB;
import com.jlmarting.earthquakes.model.EarthQuake;
import com.jlmarting.earthquakes.tasks.DownloadEarthQuakesTask;

import org.json.JSONObject;

import java.util.ArrayList;


public class EarthQuakeListFragment extends ListFragment{
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

        //ToDo: cargar preferencias
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getActivity().getBaseContext());

        //this.updateEarthQuakes();


    }

    @Override
    public void onResume() {
        super.onResume();
        //Leemos settings
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getActivity().getBaseContext());
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = super.onCreateView(inflater, container, savedInstanceState);
        earthQuakes = new ArrayList<>();
       // aa= new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,this.earthQuakes);
       // aa= new EarthQuakeAdapter(getActivity(),R.layout.earthquake_layout,this.earthQuakes);
        EarthQuakeDB earthQuakeDB=new EarthQuakeDB(getActivity());
        aa= new EarthQuakeAdapter(getActivity(),R.layout.earthquake_layout,earthQuakeDB.selectAll());
        setListAdapter(aa);
        return layout;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("id", earthQuakes.get(position).get_id());
        startActivity(intent);
    }

    /*@Override
    public void addEarthQuake(EarthQuake earthQuake) {
        earthQuakes.add(0,earthQuake);
        aa.notifyDataSetChanged();
    }*/
/*
    @Override
   public void notifyTotal(int total) {
        Log.d("TOTAL", String.valueOf(total));
        String msg = getString(R.string.num_earthquakes, total);

        Toast t = Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT);
        t.show();

    }*/
}
