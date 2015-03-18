package com.jlmarting.todolist;

import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private static final String TODOS_KEY = "TODOS_ARRAY";
    private ArrayList<String> todos;

    private Button btnAdd;
    private TextView todoText;
    private ListView todoListView;

    //Adapter: para pasar datos entre vista y código
    private ArrayAdapter<String> aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Obtenemos los controles
        btnAdd = (Button)findViewById(R.id.buttonAdd);
        todoText = (TextView)findViewById(R.id.todoText);
        todoListView = (ListView)findViewById(R.id.todoListView);

        //Adaptador: array con todos <----> vista
        this.todos = new ArrayList<String>();
        aa = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, todos);
        todoListView.setAdapter(aa);

        //Refactorización: agrupamos listeners en esta función
        this.addEventListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        aa.notifyDataSetChanged();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);

        /*
        IMPORTANTE: si realizamos la instrucción comentada no funcionará
         */
        //todos = savedInstanceState.getStringArrayList(TODOS_KEY);
        todos.addAll(savedInstanceState.getStringArrayList(TODOS_KEY));
        Log.d("<<CHANGE>>", "onRestoreInstanceState");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putStringArrayList(TODOS_KEY,todos);
        super.onSaveInstanceState(outState);
        Log.d("<<CHANGE>>", "onSaveInstanceState " + todos.toString());
    }

    private void addEventListeners() {
        // Listener
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todo = todoText.getText().toString();
                todos.add(0,todo);

                // repintar
                aa.notifyDataSetChanged();
            }
        });
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
