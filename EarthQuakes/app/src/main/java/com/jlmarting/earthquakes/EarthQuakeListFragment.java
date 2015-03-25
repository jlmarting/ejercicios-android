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


public class EarthQuakeListFragment extends ListFragment {
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

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                /*
                Debería dar fallo, dado que desde el thread no
                es posible acceder a la vista
                 */
                updateEarthQuakes();
            }
        });

       t.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = super.onCreateView(inflater, container, savedInstanceState);
        aa= new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,this.earthQuakes);
        setListAdapter(aa);
        return layout;
    }

    private void updateEarthQuakes() {

        String earthquakesFeed = getString(R.string.earthquakes_url);

        try{
            URL url = new URL(earthquakesFeed);
            URLConnection connection = url.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection)connection;

            int	responseCode = httpConnection.getResponseCode();

            if	(responseCode == HttpURLConnection.HTTP_OK)	{
                BufferedReader streamReader = new BufferedReader(
                        new InputStreamReader(
                                httpConnection.getInputStream(), "UTF-8"));

                StringBuilder responseStrBuilder = new StringBuilder();

                String inputStr;
                while ((inputStr = streamReader.readLine()) != null)
                    responseStrBuilder.append(inputStr);

                json = new JSONObject(responseStrBuilder.toString());
                JSONArray earthquakes = json.getJSONArray("features");

                for (int i = earthquakes.length()-1; i >= 0; i--) {
                    processEarthQuakeTask(earthquakes.getJSONObject(i));
                }
            }
        }
        catch	(MalformedURLException e)	{
            Log.d("ERR", "Malformed	URL	Exception.", e);
        }
        catch	(IOException e)	{
            Log.d("ERR",	"IO	Exception.",e);
        }
        catch(JSONException e){
            Log.d("ERR",	"JSON Exception.",e);
        }

    }

    private void processEarthQuakeTask(JSONObject jsonObject) {
        try{
            String id = jsonObject.getString("id");
            JSONArray jsonCoords = jsonObject.getJSONObject("geometry").getJSONArray("coordinates");
            Coordinate coords = new Coordinate(jsonCoords.getDouble(0), jsonCoords.getDouble(1), jsonCoords.getDouble(2));
            JSONObject properties = jsonObject.getJSONObject("properties");

            EarthQuake earthQuake = new EarthQuake();
            earthQuake.set_id(jsonObject.getString("id"));
            earthQuake.setPlace(properties.getString("place"));
            earthQuake.setMagnitude(properties.getDouble("mag"));
            earthQuake.setDate(properties.getInt("time"));
            earthQuake.setUrl(properties.getString("url"));
            earthQuake.setCoords(coords);

            Log.d("EARTHQUAKE", earthQuake.toString());

            //Agregamos el dato de terremoto en la estructura de datos
            this.earthQuakes.add(0,earthQuake);
            //Debemos notificar al adaptador para que se refresque
            aa.notifyDataSetChanged();

        }
        catch(JSONException e){
            Log.d("ERR", e.toString());
        }

    }
}
