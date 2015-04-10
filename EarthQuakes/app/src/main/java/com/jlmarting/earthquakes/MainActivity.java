package com.jlmarting.earthquakes;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.jlmarting.earthquakes.database.EarthQuakeDB;
import com.jlmarting.earthquakes.managers.EarthQuakeAlarmManager;
import com.jlmarting.earthquakes.tasks.DowloadEarthQuakesTask;


public class MainActivity extends ActionBarActivity implements DowloadEarthQuakesTask.AddEarthQuakeInterface {

    private static final int PREFS_ACTIVITY = 1 ;
    private EarthQuakeDB dbHelper;
    private final String EARTHQUAKE_PREFS= "EARTHQUAKE_PREFS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        String KEY = "LAUNCHED_BEFORE";
        SharedPreferences prefs= getSharedPreferences(EARTHQUAKE_PREFS, Activity.MODE_PRIVATE);

        if(!prefs.getBoolean(KEY, false));{
            long interval = getResources().getInteger(R.integer.default_interval)* 60 * 100;
            EarthQuakeAlarmManager.setAlarm(this,interval);
            prefs.edit().putBoolean(KEY, true).apply();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
       // dbHelper.queryAll();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent prefIntent;
            prefIntent = new Intent(this, SettingsActivity.class);
            startActivityForResult(prefIntent, PREFS_ACTIVITY);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void notifyTotal(int Total) {
        String msg= getString(R.string.Mensaje,Total);
        Toast toast=  Toast.makeText( this,msg + Total, Toast.LENGTH_LONG);
        toast.show();
    }

}
