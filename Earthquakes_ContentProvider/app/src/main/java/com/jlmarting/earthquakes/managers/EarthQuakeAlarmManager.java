package com.jlmarting.earthquakes.managers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.jlmarting.earthquakes.R;
import com.jlmarting.earthquakes.services.DownloadEarthQuakesService;

/**
 * Created by javi-vaquero on 1/04/15.
 */
public class EarthQuakeAlarmManager {

    public static void setAlarm(Context context, long interval){
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(context.ALARM_SERVICE);
        int	alarmType = AlarmManager.RTC;
        Intent intentToFire = new Intent(context, DownloadEarthQuakesService.class);
        PendingIntent alarmIntent = PendingIntent.getService(context, 0, intentToFire, 0);

        alarmManager.setInexactRepeating(alarmType, interval, interval, alarmIntent);

    }

    public static void cancelAlarm(Context context){
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(context.ALARM_SERVICE);
        Intent intentToFire = new Intent(context, DownloadEarthQuakesService.class);
        PendingIntent alarmIntent = PendingIntent.getService(context, 0, intentToFire, 0);

        alarmManager.cancel(alarmIntent);
    }

    public static void updateAlarm(Context context, long interval){
        setAlarm(context, interval);
    }

}
