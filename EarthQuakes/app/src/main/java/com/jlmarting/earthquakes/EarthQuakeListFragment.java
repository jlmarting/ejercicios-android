package com.jlmarting.earthquakes;

import android.app.ListFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.jlmarting.earthquakes.adapter.EarthQuakeAdapter;
import com.jlmarting.earthquakes.database.EarthQuakeDB;
import com.jlmarting.earthquakes.model.EarthQuake;

import org.json.JSONObject;

import java.util.ArrayList;


public class EarthQuakeListFragment extends ListFragment {

    private ArrayList<EarthQuake> earthQuakes;
    private ArrayAdapter<EarthQuake> aa;
    private SharedPreferences prefs;
    private EarthQuakeDB earthQuakeDB;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.earthQuakes = new ArrayList<>();
        this.prefs = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        this.earthQuakeDB = new EarthQuakeDB(getActivity());
     }

    @Override
    public void onResume() {
        super.onResume();
        prefs = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        earthQuakes.clear();
        earthQuakes.addAll(this.earthQuakeDB.selectAll());
        aa.notifyDataSetChanged();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout = super.onCreateView(inflater, container, savedInstanceState);

        aa = new EarthQuakeAdapter(getActivity(), R.layout.earthquake_layout, this.earthQuakes);
        Log.d("EALF", this.earthQuakes.toString());
        //Provide the cursor for the list view.
        this.setListAdapter(aa);
        return layout;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Intent intent = new Intent(getActivity(), DetailActivity.class);
        //Log.d("FREAID",String.valueOf(position));
        intent.putExtra("id", earthQuakes.get(position).get_id());
        //intent.putExtra("place",earthQuakes.get(position).getPlace());
        //intent.putExtra("mag",earthQuakes.get(position).getMagnitude());
        startActivity(intent);
    }

}
