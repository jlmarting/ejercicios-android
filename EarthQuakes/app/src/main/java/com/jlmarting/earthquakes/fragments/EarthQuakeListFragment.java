package com.jlmarting.earthquakes.fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.ListFragment;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.jlmarting.earthquakes.EarthQuakeActivityDetail;
import com.jlmarting.earthquakes.R;

import com.jlmarting.earthquakes.database.EarthQuakeDB;
import com.jlmarting.earthquakes.model.EarthQuake;

import java.util.ArrayList;

import com.jlmarting.earthquakes.adapters.earthQuakesAdapter;

import static android.widget.Toast.makeText;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the //{@link //OnFragmentInteractionListener}
 * interface.
 */
public class EarthQuakeListFragment extends ListFragment
        //implements DowloadEarthQuakesTask.AddEarthQuakeInterface

{

    private SharedPreferences prefs = null;
    public static final String EARTHQUAKES_ITEM = "EARTHQUAKES";
    private static final String DATA = "datos";

    private EarthQuakeDB db;


    private ArrayList<EarthQuake> earthQuakes;
    private ArrayAdapter<EarthQuake> aa;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs= PreferenceManager.getDefaultSharedPreferences(getActivity());
        db = new EarthQuakeDB(getActivity());




        //prefs = PreferenceManager

       /* DowloadEarthQuakesTask task = new DowloadEarthQuakesTask(getActivity(), this);

        task.execute(getString(R.string.earthquakesurl));*/

       /* Thread t=  new Thread(new Runnable() {
            @Override
            public void run() {
                updatedEarthQuake();


            }
        });
        t.start();*/

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = super.onCreateView(inflater, container, savedInstanceState);

        //aa = new earthQuakesAdapter(getActivity(),R.layout.earthquakes_list,earthQuakes);


        earthQuakes = new ArrayList<EarthQuake>();
        aa = new earthQuakesAdapter(getActivity(), R.layout.earthquakes_list, earthQuakes);

        if (savedInstanceState != null) {

            ArrayList<EarthQuake> tmp = savedInstanceState.getParcelableArrayList(DATA);

            if (tmp != null) {

                earthQuakes.addAll(tmp);
            }
        }


        setListAdapter(aa);

        return layout;

    }


    @Override
    public void onResume() {
        super.onResume();


        int minMag = Integer.parseInt(prefs.getString(getString(R.string.magnitude), "0"));

        earthQuakes.clear();
        earthQuakes.addAll(db.getAllByMagnitude(minMag));

        aa.notifyDataSetChanged();
    }



    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        EarthQuake earthQuake = earthQuakes.get(position);
       Intent detailIntent = new Intent(getActivity(), EarthQuakeActivityDetail.class);

        detailIntent.putExtra(EARTHQUAKES_ITEM, earthQuake.get_id());
        startActivity(detailIntent);
    }


}