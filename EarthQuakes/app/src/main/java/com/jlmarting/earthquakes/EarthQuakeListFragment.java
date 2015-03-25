package com.jlmarting.earthquakes;

import android.app.Activity;
import android.os.Bundle;
import android.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.jlmarting.earthquakes.dummy.DummyContent;

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

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class EarthQuakeListFragment extends ListFragment {

    /*
    Realmente no tenemos porque esperar a que la vista esté creada para realizar 
    la conexión, puesto que puede tardar
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        updateEarthQuakes();
    }

    private void updateEarthQuakes() {
        String earthquakesFeed = getString(R.string.earthquakes_url);
        JSONObject json;

        try{
            URL url = new URL(earthquakesFeed);

            URLConnection connection = url.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection)connection;

            int	responseCode	=	httpConnection.getResponseCode();

            if	(responseCode	==	HttpURLConnection.HTTP_OK)	{
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

    }
}
