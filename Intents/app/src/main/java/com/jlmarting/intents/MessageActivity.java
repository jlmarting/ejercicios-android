package com.jlmarting.intents;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


public class MessageActivity extends ActionBarActivity {

    /* TODO
        agrupar en método processIntent las acciones de datos
        implementar el botón cancel
        onclick
            setResult(RESULT_CANCELED);
            finish();
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        // ----
        Intent intent = this.getIntent();
        String msg = intent.getStringExtra(MainActivity.MSG).toString();
        Log.d("MSG", msg);
        TextView tvMsg = (TextView)findViewById(R.id.lblMsg);
        tvMsg.setText(msg);

        Button btnSend = (Button)findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText etMsg = (EditText)findViewById(R.id.etMsg);
                String msg = etMsg.getText().toString();
                if(msg.length()>0){
                    Intent intent = new Intent(getApplicationContext(),MessageActivity.class);
                    intent.putExtra(MainActivity.MSG, msg);

                    setResult(RESULT_OK, intent);
                    finish();
                    //AQuí ya no es necesario iniciar actividades... ya estaba activa la MainActivity
                }
                else {
                    //TOAST: mensajes en pantalla
                    Toast toast = Toast.makeText(MessageActivity.this,
                            getResources().getString(R.string.no_text),
                            Toast.LENGTH_LONG);
                    toast.show();

                }





                Intent intent1 = new Intent();

                intent1.putExtra("MSG","aaa");
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_message, menu);
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
