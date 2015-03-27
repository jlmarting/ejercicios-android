package com.jlmarting.earthquakes;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.jlmarting.earthquakes.model.EarthQuake;
import com.jlmarting.earthquakes.tasks.DownloadEarthQuakesTask;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements DownloadEarthQuakesTask.AddEarthQuakeInterface {

    private static final int PREFS_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // El activity_main instanciará un fragment. La lógica de la app estará ahí
        setContentView(R.layout.activity_main);

        updateEarthQuakes();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            Intent intent = new Intent(this,SettingsActivity.class);
            startActivityForResult(intent, PREFS_ACTIVITY);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void addEarthQuake(EarthQuake earthQuake) {

    }

    @Override
    public void notifyTotal(int total) {
        Log.d("TOTAL", String.valueOf(total));
        String msg = getString(R.string.num_earthquakes, total);

        Toast t = Toast.makeText(this,msg,Toast.LENGTH_SHORT);
        t.show();

    }

    public void updateEarthQuakes(){
        //earthQuakes = new ArrayList<>();

        DownloadEarthQuakesTask task = new DownloadEarthQuakesTask(this.getBaseContext());
        //los asynctask se ponen en marcha con execute
        task.execute(getString(R.string.earthquakes_url));

    }
}
