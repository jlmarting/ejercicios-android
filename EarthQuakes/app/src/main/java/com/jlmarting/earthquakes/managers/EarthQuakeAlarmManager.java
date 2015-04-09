package com.jlmarting.earthquakes.managers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.jlmarting.earthquakes.services.DownloadEarthQuakesService;

/**
 * Created by cursomovil on 1/04/15.
 */
public class EarthQuakeAlarmManager {


    public  static void setAlarm(Context context, long interval){


        android.app.AlarmManager alarmManager = (android.app.AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        int alarmtype = AlarmManager.RTC;
        //long timerOrLengthofWait = AlarmManager.INTERVAL_FIFTEEN_MINUTES;
        long timerOrLengthofWait = 1000;
        String ALARM_ACTION = "ALARM_ACTION";
        // Intent intent = new Intent(ALARM_ACTION);
        Intent intent = new Intent(context, DownloadEarthQuakesService.class);
         PendingIntent alarmIntent = PendingIntent.getService(context, 0, intent, 0);
        alarmManager.setInexactRepeating(alarmtype, interval, interval, alarmIntent);
        //Log.d("CHANGE", String.valueOf(context));


    }
    public  static  void stopAlarm(Context context){

        android.app.AlarmManager alarmManager = (android.app.AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        Intent intent = new Intent(context, DownloadEarthQuakesService.class);
        PendingIntent alarmIntent = PendingIntent.getService(context, 0, intent, 0);
        alarmManager.cancel(alarmIntent);

    }


    public  static  void updateAlarm(Context context, long interval){
        setAlarm(context, interval);
       // Log.d("CHANGE", String.valueOf(context));

    }

}
