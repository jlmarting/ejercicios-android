package com.jlmarting.calculadora;

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


public class MainActivity extends ActionBarActivity implements View.OnClickListener{

    public CalcLogic calc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.calc = new CalcLogic();

        //Obtener elementos
        Button btnNum0 = (Button)findViewById(R.id.btnNum0);
        Button btnNum1 = (Button)findViewById(R.id.btnNum1);
        Button btnNum2 = (Button)findViewById(R.id.btnNum2);
        Button btnNum3 = (Button)findViewById(R.id.btnNum3);
        Button btnNum4 = (Button)findViewById(R.id.btnNum4);
        Button btnNum5 = (Button)findViewById(R.id.btnNum5);
        Button btnNum6 = (Button)findViewById(R.id.btnNum6);
        Button btnNum7 = (Button)findViewById(R.id.btnNum7);
        Button btnNum8 = (Button)findViewById(R.id.btnNum8);
        Button btnNum9 = (Button)findViewById(R.id.btnNum9);

        Button btnAdd = (Button)findViewById(R.id.btnAdd);
        Button btnSub = (Button)findViewById(R.id.btnSub);
        Button btnMul = (Button)findViewById(R.id.btnMul);
        Button btnDiv = (Button)findViewById(R.id.btnDiv);
        Button btnResult = (Button)findViewById(R.id.btnResult);

        TextView screen = (TextView)findViewById(R.id.screen);

        btnNum0.setOnClickListener(this);
        btnNum1.setOnClickListener(this);
        btnNum2.setOnClickListener(this);
        btnNum3.setOnClickListener(this);
        btnNum4.setOnClickListener(this);
        btnNum5.setOnClickListener(this);
        btnNum6.setOnClickListener(this);
        btnNum7.setOnClickListener(this);
        btnNum8.setOnClickListener(this);
        btnNum9.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnSub.setOnClickListener(this);
        btnMul.setOnClickListener(this);
        btnDiv.setOnClickListener(this);
        btnResult.setOnClickListener(this);
    }

    public void writeScreen(String val){
        TextView screen = (TextView)findViewById(R.id.screen);
        screen.setText(screen.getText()+val);
    }

    public void operate(){

    }

    @Override
    public void onClick(View v) {
        Log.d("DEBUG", "Click - " + v.getId());
        switch(v.getId()){
            case R.id.btnNum0 : this.writeScreen("0");break ;
            case R.id.btnNum1 : this.writeScreen("1");break ;
            case R.id.btnNum2 : this.writeScreen("2");break ;
            case R.id.btnNum3 : this.writeScreen("3");break ;
            case R.id.btnNum4 : this.writeScreen("4");break ;
            case R.id.btnNum5 : this.writeScreen("5");break ;
            case R.id.btnNum6 : this.writeScreen("6");break ;
            case R.id.btnNum7 : this.writeScreen("7");break ;
            case R.id.btnNum8 : this.writeScreen("8");break ;
            case R.id.btnNum9 : this.writeScreen("9");break ;
            default: ;



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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
