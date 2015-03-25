package com.jlmarting.earthquakes.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.jlmarting.earthquakes.R;
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
 * Created by cursomovil on 25/03/15.
 */
public class DownloadEarthQuakesTask extends AsyncTask<String,EarthQuake,Integer> {

    /*Implementamos una interfaz para pasar datos
        La interfaz es como un tipo de datos
     */
    public interface AddEarthQuakeInterface{
        public void addEarthQuake(EarthQuake earthQuake);
        //posteriormente en el constructor "obligamos" a que se utilice
    }

    private AddEarthQuakeInterface target;
    private String EARTHQUAKE = "EARTHQUAKE";


    public DownloadEarthQuakesTask(AddEarthQuakeInterface target){
        this.target = target;
    }

    @Override
    protected Integer doInBackground(String... urls) {
        // Este método es equivalente al thread anterior

        if(urls.length>0) {
            updateEarthQuakes(urls[0]);
        }
        return null;
    }


    private void updateEarthQuakes(String earthquakesFeed) {
        JSONObject json;
       // String earthquakesFeed = getString(R.string.earthquakes_url);

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

    @Override
    protected void onProgressUpdate(EarthQuake... earthQuakes) {
        super.onProgressUpdate(earthQuakes);

        target.addEarthQuake(earthQuakes[0]);

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
            publishProgress(earthQuake);
            //Agregamos el dato de terremoto en la estructura de datos
            //this.earthQuakes.add(0,earthQuake);
            //Debemos notificar al adaptador para que se refresque
            //aa.notifyDataSetChanged();

        }
        catch(JSONException e){
            Log.d("ERR", e.toString());
        }

    }
}
