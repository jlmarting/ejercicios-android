package com.jlmarting.earthquakes;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.jlmarting.earthquakes.database.EarthQuakeDB;
import com.jlmarting.earthquakes.model.EarthQuake;


public class DetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        EarthQuakeDB db = new EarthQuakeDB(this);
        EarthQuake ea = db.selectById(getIntent().getStringExtra("id"));

        TextView tvPlace = (TextView)findViewById(R.id.tvPlace);
        TextView tvMag = (TextView)findViewById(R.id.tvMag);
        tvPlace.setText(ea.getPlace());
        tvMag.setText(String.valueOf(ea.getMagnitude()));

        Log.d("DETAIL-EA", ea.getPlace() + " - " + ea.getMagnitude());



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}