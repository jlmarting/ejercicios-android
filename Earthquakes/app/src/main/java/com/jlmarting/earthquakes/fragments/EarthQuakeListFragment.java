package com.jlmarting.earthquakes.fragments;

import android.app.ListFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.preference.PreferenceManager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.jlmarting.earthquakes.DetailActivity;
import com.jlmarting.earthquakes.R;
import com.jlmarting.earthquakes.adapters.EarthQuakeArrayAdapter;
import com.jlmarting.earthquakes.database.EarthQuakeDB;
import com.jlmarting.earthquakes.model.EarthQuake;
import com.jlmarting.earthquakes.services.DownloadEarthQuakesService;

import java.util.ArrayList;


/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the {@ link OnFragmentInteractionListener}
 * interface.
 */
public class EarthQuakeListFragment extends ListFragment {

    public static final String EQ_ID = "ID";


    private SharedPreferences prefs;
    private EarthQuakeDB earthquakeDB;

    private ArrayList<EarthQuake> earthQuakeArrayList;// = new ArrayList<>();
    private EarthQuakeArrayAdapter aa;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        earthquakeDB = new EarthQuakeDB(getActivity());
        earthQuakeArrayList = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout =  super.onCreateView(inflater, container, savedInstanceState);

        aa= new EarthQuakeArrayAdapter(getActivity(), R.layout.earthquake_item, earthQuakeArrayList);
        setListAdapter(aa);


        return layout;

    }

    @Override
    public void onResume() {
        super.onResume();

        double magnitude = new Double(prefs.getString(getString(R.string.PREF_MIN_MAGNITUDE), "0.0"));
        earthQuakeArrayList.clear();
        earthQuakeArrayList.addAll(earthquakeDB.getAllByMagnitude(magnitude));
        aa.notifyDataSetChanged();

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Intent detailIntent = new Intent(getActivity(), DetailActivity.class);
        detailIntent.putExtra(EQ_ID, earthQuakeArrayList.get(position).get_id());
        startActivity(detailIntent);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_refresh,menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id==R.id.action_refresh){
            Intent download = new Intent(getActivity(), DownloadEarthQuakesService.class);
            getActivity().startService(download);
        }
        return super.onOptionsItemSelected(item);
    }
}
