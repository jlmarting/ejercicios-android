package com.jlmarting.earthquakes.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.jlmarting.earthquakes.providers.EarthQuakeDB;
import com.jlmarting.earthquakes.model.Coordinate;
import com.jlmarting.earthquakes.model.EarthQuake;

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
 * Created by javi-vaquero on 25/03/15.
 */
public class DownloadEarthQuakesTask extends AsyncTask<String,EarthQuake,Integer> {

    private EarthQuakeDB earthQuakeDB;

    public interface AddEarthQuakeInterface{
        public void notifyTotal(int total);
    }

    private final String EARTHQUAKE = "EARTHQUAKE";

    private AddEarthQuakeInterface target;

    public DownloadEarthQuakesTask(Context context, AddEarthQuakeInterface target){
        this.target=target;
        earthQuakeDB = new EarthQuakeDB(context);
    }

    @Override
    protected Integer doInBackground(String... urls) {
        int count = 0;
        if(urls.length>0) {
            count = updateEarthQuakes(urls[0]);
        }

        return new Integer(count);
    }

    @Override
    protected void onProgressUpdate(EarthQuake... earthquakes) {
        super.onProgressUpdate(earthquakes);

    }

    @Override
    protected void onPostExecute(Integer count) {
        super.onPostExecute(count);
        target.notifyTotal(count.intValue());

    }

    private int updateEarthQuakes(String earthQuakeFeed) {
        JSONObject json;
        String earthquakeUrl = earthQuakeFeed;
        int count = 0;

        try{
            URL url = new URL(earthquakeUrl);

            //	Create	a	new	HTTP	URL	connection
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
                count=earthquakes.length();

                for (int i = earthquakes.length()-1; i >= 0; i--) {
                    processEarthQuakeTask(earthquakes.getJSONObject(i));
                }


            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return count;

    }


    private void processEarthQuakeTask(JSONObject jsonObject) {
        try {
            //Get coordinates
            JSONArray jsonCoords = jsonObject.getJSONObject("geometry").getJSONArray("coordinates");
            Coordinate coords = new Coordinate(jsonCoords.getDouble(0), jsonCoords.getDouble(1),jsonCoords.getDouble(2));

            //Get properties
            JSONObject properties = jsonObject.getJSONObject("properties");

            //EarthQuake
            EarthQuake earthQuake = new EarthQuake();
            earthQuake.set_id(jsonObject.getString("id"));
            earthQuake.setPlace(properties.getString("place"));
            earthQuake.setMagnitude(properties.getDouble("mag"));
            earthQuake.setDate(properties.getLong("time"));
            earthQuake.setUrl(properties.getString("url"));
            earthQuake.setCoords(coords);

            Log.d(EARTHQUAKE, earthQuake.toString());
            earthQuakeDB.insertEarthQuake(earthQuake);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
