package com.jlmarting.earthquakes.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.IBinder;
import android.util.Log;

import com.jlmarting.earthquakes.MainActivity;
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

public class DownloadEarthQuakesService extends Service {

    private final String EARTHQUAKE = "EARTHQUAKE";
    private EarthQuakeDB earthQuakeDB;
    @Override
    public void onCreate() {
        super.onCreate();

        earthQuakeDB = new EarthQuakeDB(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Log.d("UPDATES","updating");

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                updateEarthQuakes(getString(R.string.earthquakes_url));
            }
        });
        t.start();

        return Service.START_STICKY;
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

                sendNotification(count);

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

    private void sendNotification(int count) {
        Intent intentToFire = new Intent(this, MainActivity.class);
        PendingIntent activityIntent = PendingIntent.getActivity(this, 0, intentToFire, 0);

        Notification.Builder builder = new Notification.Builder(DownloadEarthQuakesService.this);
        builder.setSmallIcon(R.drawable.ic_launcher)
                    .setTicker(getString(R.string.app_name))
                    .setContentText(getString(R.string.count_earthquakes, count))
                    .setWhen(System.currentTimeMillis())
                    .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setVibrate(new	long[]{ 1000, 1000, 1000, 1000, 1000})
                    .setContentIntent(activityIntent)
                    .setAutoCancel(true);

        Notification notification = builder.build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        int	NOTIFICATION_REF = 1;

        notificationManager.notify(NOTIFICATION_REF, notification);

    }


    private void processEarthQuakeTask(JSONObject jsonObject) {
        try {
            //Get coordinates
            JSONArray jsonCoords = jsonObject.getJSONObject("geometry").getJSONArray("coordinates");
            //Latitude and Longitude vienen primero Longitud y luego Latitud
            Coordinate coords = new Coordinate(jsonCoords.getDouble(1), jsonCoords.getDouble(0),jsonCoords.getDouble(2));

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



    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
