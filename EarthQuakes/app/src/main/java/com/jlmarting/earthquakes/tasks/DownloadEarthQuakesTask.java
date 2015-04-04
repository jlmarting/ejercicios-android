package com.jlmarting.earthquakes.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.jlmarting.earthquakes.database.EarthQuakeDB;
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
 * Un AsyncTask tiene sentido cuando tenemos que sincronizarlo con la vista.
 * Sino usaríamos un thread normal.
 */
public class DownloadEarthQuakesTask extends AsyncTask<String, EarthQuake, Integer> {


    //Declaramos una interfaz para pasar datos. La interfaz es como un tipo de datos
    public interface AddEarthQuakeInterface {
       // public void addEarthQuake(EarthQuake earthQuake);
        public void notifyTotal(int total);
    }

    public static EarthQuakeDB earthQuakeDB;
    private AddEarthQuakeInterface target;
    private String EARTHQUAKE = "EARTHQUAKE";




    public DownloadEarthQuakesTask(Context context, AddEarthQuakeInterface target) {
        this.target = target; //Obligamos en el constructor a implementar el interfaz
        this.earthQuakeDB = new EarthQuakeDB(context);
    }


    @Override
    protected Integer doInBackground(String... urls) {
        int count = 0;
        // Este método es equivalente al thread anterior

        if (urls.length > 0) {
            count = updateEarthQuakes(urls[0]);
        }
        return count;
    }


    //En el override hemos modificado el return de void a int
    private int updateEarthQuakes(String earthquakesFeed) {
        JSONObject json;
        int count = 0;

        try {
            URL url = new URL(earthquakesFeed);
            URLConnection connection = url.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection) connection;

            int responseCode = httpConnection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {

                // Lectura JSON
                BufferedReader streamReader = new BufferedReader(
                        new InputStreamReader(
                                httpConnection.getInputStream(), "UTF-8"));
                /*
                StringBuilder
                A modifiable sequence of characters for use in creating strings.
                This class is intended as a direct replacement of StringBuffer for
                non-concurrent use; unlike StringBuffer this class is not synchronized.
                 */
                StringBuilder responseStrBuilder = new StringBuilder();
                String inputStr;

                while ((inputStr = streamReader.readLine()) != null)
                    responseStrBuilder.append(inputStr);

                json = new JSONObject(responseStrBuilder.toString());
                JSONArray earthquakes = json.getJSONArray("features");

                for (int i = earthquakes.length() - 1; i >= 0; i--) {
                    processEarthQuakeTask(earthquakes.getJSONObject(i));
                }
                count = earthquakes.length();

            }
        } catch (MalformedURLException e) {
            Log.d("ERR", "Malformed	URL	Exception.", e);
        } catch (IOException e) {
            Log.d("ERR", "IO	Exception.", e);
        } catch (JSONException e) {
            Log.d("ERR", "JSON Exception.", e);
        }
        return count;
    }

    @Override
    protected void onProgressUpdate(EarthQuake... earthQuakes) {
        super.onProgressUpdate(earthQuakes);
        // target.addEarthQuake(earthQuakes[0]);
        // Si hacemos esto aquí bloqueamos el thread...
    }

    /*
    Toma un objeto JSON para publicarlo como progreso de la tarea (publishProgress) e
    insertarlo en la base de datos.
     */
    private void processEarthQuakeTask(JSONObject jsonObject) {
        try {
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

            Log.d("TASK", earthQuake.toString());
            publishProgress(earthQuake);

            //Código anterior a la existencia de base de datos
            //Agregamos el dato de terremoto en la estructura de datos
            //this.earthQuakes.add(0,earthQuake);
            //Debemos notificar al adaptador para que se refresque
            //aa.notifyDataSetChanged();

            this.earthQuakeDB.insert(earthQuake);

        } catch (JSONException e) {
            Log.d("TASK", e.toString());
        }
    }

    @Override
    protected void onPostExecute(Integer total) {
        super.onPostExecute(total);
        target.notifyTotal(total);
        this.earthQuakeDB.selectAll();

    }


}
