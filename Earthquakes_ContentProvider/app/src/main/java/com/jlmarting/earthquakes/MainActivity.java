package com.jlmarting.earthquakes;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.jlmarting.earthquakes.fragments.EarthQuakeListFragment;
import com.jlmarting.earthquakes.fragments.EarthQuakesMapListFragment;
import com.jlmarting.earthquakes.listeners.TabListener;
import com.jlmarting.earthquakes.managers.EarthQuakeAlarmManager;
import com.jlmarting.earthquakes.tasks.DownloadEarthQuakesTask;


public class MainActivity extends Activity implements DownloadEarthQuakesTask.AddEarthQuakeInterface {

    private static final String SELECTED_TAB = "SELECTED_TAB";
    private final String EARTHQUAKE_PREFS = "EARTHQUAKE_PREFS";

    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addTabs();
        checkToSetAlarm();

        if(savedInstanceState!=null){
            actionBar.setSelectedNavigationItem(savedInstanceState.getInt(SELECTED_TAB));
        }
    }

    private void addTabs() {
        actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.Tab listTab = actionBar.newTab();

        listTab
                //.setText(R.string.listTab)
                .setIcon(R.drawable.ic_action_view_as_list)
                .setTabListener(
                        new TabListener<>(this, R.id.fragmentContainer, EarthQuakeListFragment.class));
        actionBar.addTab(listTab);

        ActionBar.Tab mapTab = actionBar.newTab();

        mapTab
                //.setText(R.string.mapTab)
                .setIcon(R.drawable.ic_action_map)
                .setTabListener(
                        new TabListener<>(this, R.id.fragmentContainer, EarthQuakesMapListFragment.class));

        actionBar.addTab(mapTab);

    }

    private void checkToSetAlarm() {
        String KEY = "LAUNCHED_BEFORE";
        SharedPreferences prefs = getSharedPreferences(EARTHQUAKE_PREFS, Activity.MODE_PRIVATE);
        if(!prefs.getBoolean(KEY, false)){
            //set alarm
            long interval =  getResources().getInteger(R.integer.default_interval) * 60 * 1000;
            EarthQuakeAlarmManager.setAlarm(this,interval);
            //set LAUNCHED_BEFORE = true
            prefs.edit().putBoolean(KEY, true).apply();
        }
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
            Intent prefsIntent = new Intent(this, SettingsActivity.class);
            startActivity(prefsIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(SELECTED_TAB, actionBar.getSelectedNavigationIndex());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        actionBar.setSelectedNavigationItem(savedInstanceState.getInt(SELECTED_TAB));
    }

    @Override
    public void notifyTotal(int total) {
        String msg = getString(R.string.num_earthquakes, total);
        Toast t =  Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        t.show();
    }
}
