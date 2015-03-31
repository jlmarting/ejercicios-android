package services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.jlmarting.earthquakes.R;
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

/*
Mediante este servicio trataremos de realizar consultas periódicas
de terremotos
 */
public class DownloadEarthQuakeService extends Service {

    private EarthQuakeDB earthQuakeDB;

    public DownloadEarthQuakeService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        earthQuakeDB = new EarthQuakeDB(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                updateEarthQuakes(getString(R.string.earthquakes_url));
            }
        });
        t.start();
        return Service.START_STICKY;
    }



    private int updateEarthQuakes(String earthquakesFeed) {
        JSONObject json;
        int count = 0;
        // String earthquakesFeed = getString(R.string.earthquakes_url);

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

            Log.d("EARTHQUAKE", earthQuake.toString());
            //publishProgress(earthQuake);
            //Agregamos el dato de terremoto en la estructura de datos
            //this.earthQuakes.add(0,earthQuake);
            //Debemos notificar al adaptador para que se refresque
            //aa.notifyDataSetChanged();

            this.earthQuakeDB.insert(earthQuake);

        } catch (JSONException e) {
            Log.d("ERR", e.toString());
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
       return null;
    }
}
