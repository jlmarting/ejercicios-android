package com.jlmarting.calculadora;

import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jlmarting.calculadora.calcLogic.CalcLogic;


public class MainActivity extends ActionBarActivity implements numbersPad.OnFragmentInteractionListener
, opsPad.OnFragmentInteractionListener, screen.OnFragmentInteractionListener{

    public CalcLogic calc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

   }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.d("LISTENER", uri.toString());

    }
}
