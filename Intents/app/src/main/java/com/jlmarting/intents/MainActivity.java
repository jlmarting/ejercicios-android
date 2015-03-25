package com.jlmarting.intents;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    /*
    TODOS:
    crear un método addEventListeners() para centralizar desde ahí la gestión de Listeners

    getApplicationContext <---> MainActivity.this

    para el uso de la cámara debería introducirse declaraciones en el manifiesto

     */

    private static final int REQUEST_TARGET = 1;
    public static final String MSG = "MSG";
    private static final int REQUEST_PHOTO = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnSend = (Button)findViewById(R.id.btnSendMsg);
        Button btnPhoto = (Button)findViewById(R.id.btnPhoto);


        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                if(takePictureIntent.resolveActivity(getPackageManager())!=null){
                    startActivityForResult(takePictureIntent, REQUEST_PHOTO);
                }

            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etMsg = (EditText)findViewById(R.id.etMsg);
                String msg = etMsg.getText().toString();

                if(msg.length()>0){
                    Intent intent = new Intent(getApplicationContext(),MessageActivity.class);
                    intent.putExtra(MSG, msg);

                    /*
                    Indicamos un código para poder identificar después la respuesta
                    es decir, de que actividad nos vienen los datos
                    */
                    startActivityForResult(intent, REQUEST_TARGET);
                }
                else {
                    //TOAST: mensajes en pantalla
                    Toast toast = Toast.makeText(MainActivity.this,
                            getResources().getString(R.string.no_text),
                            Toast.LENGTH_LONG);
                    toast.show();

                }

            }
        });

    }

    /* Este método siempre se ejecuta al finalizar una actividad,
    analizaremos el código y actuaremos en consecuencua
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode!=RESULT_CANCELED){
            switch(requestCode){
                case REQUEST_TARGET:
                    String msg = data.getStringExtra(MSG);
                    TextView lblMsg = (TextView)findViewById(R.id.lblMsg);
                    Log.d("MSG",msg);
                    //TODO: fallo al asignar el texto, revisar
                    lblMsg.setText(msg);
                    break;
                case REQUEST_PHOTO:
                    Log.d("REQUEST_PHOTO","Mostrando foto....");
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap)extras.get("data");
                    ImageView imageView = (ImageView)findViewById(R.id.imageView);
                    imageView.setImageBitmap(imageBitmap);
                    break;

            }
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
