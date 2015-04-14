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

    private EarthQuakeDB earthQuakeDB;

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
                    updatedEarthQuake(getString(R.string.earthquakesurl));
                }
            });
            t.start();

        return Service.START_STICKY;
    }

    private void sendNotification(int count){
        Intent intentToFire = new Intent(this, MainActivity.class);
        PendingIntent activityIntent = PendingIntent.getActivity(this,0,intentToFire,0);

        Notification.Builder  builder = new Notification.Builder(DownloadEarthQuakesService.this);

        builder.setSmallIcon(R.drawable.ic_launcher)
                .setContentText("App")  // ToDo: ¿qué pasa con R.string.app_name?
                .setContentText(count + " new earthquakes")
                .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_SOUND)
                .setContentIntent(activityIntent);

        Notification notification = builder.build();

        NotificationManager notificationManager =
                (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        int NOTIFICATION_REF=1;
        notificationManager.notify(NOTIFICATION_REF,notification);

    }

    private Integer updatedEarthQuake(String earthquakesFeed) {

        JSONObject json;
        Integer count = 0;


        //earthquakesFeed = new URL(earthquakesFeed);
        // String earthquakesFeed= getString(R.string.earthquakesurl);

        try {

            URL url = new URL(earthquakesFeed);
            //	Create	a	new	HTTP	URL	connection
            URLConnection connection = url.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            int responseCode = httpConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {


                BufferedReader streamReader = new BufferedReader(
                        new InputStreamReader(
                                httpConnection.getInputStream(), "UTF-8"));
                StringBuilder responseStrBuilder = new StringBuilder();

                String inputStr;
                while ((inputStr = streamReader.readLine()) != null)
                    responseStrBuilder.append(inputStr);

                json = new JSONObject(responseStrBuilder.toString());
                JSONArray earthquakes = json.getJSONArray("features");
                count = earthquakes.length();

                for (int i = earthquakes.length() - 1; i >= 0; i--) {
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
            String id = jsonObject.getString("id");
            JSONArray jSoncoords = jsonObject.getJSONObject("geometry").getJSONArray("coordinates");
            Coordinate coords = new Coordinate(jSoncoords.getDouble(0), jSoncoords.getDouble(1), jSoncoords.getDouble(2));
            JSONObject properties = jsonObject.getJSONObject("properties");

            EarthQuake earthQuake = new EarthQuake();
            //earthQuake.set_id(properties.getString("id"));
            earthQuake.set_id(id);
            earthQuake.setPlace(properties.getString("place"));
            earthQuake.setMagnitude(properties.getDouble("mag"));
            earthQuake.setTime(properties.getLong("time"));
            earthQuake.setUrl(properties.getString("url"));
            earthQuake.setCoords(coords);

            Log.d("EQ", earthQuake.toString());

           /*Necesario para conectarse con la vista, llama a la funcion on ProgressUpdate, para esto es necesario la
           * interface*/
            //publishProgress(earthQuake);
            //earthQuakes.add(0,earthQuake);
            //aa.notifyDataSetChanged();

            earthQuakeDB.createRow(earthQuake);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    @Override
    public IBinder onBind(Intent intent) {
       return null;
    }




}
