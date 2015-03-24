package com.jlmarting.todolistfragment;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.jlmarting.todolistfragment.fragments.TodoListFragment;
import com.jlmarting.todolistfragment.model.ToDo;

/* Esta actividad muestra en detalle una tarea

 */

public class DetailActivity extends ActionBarActivity {


    private ToDo todo;
    private TextView lblTask;
    private TextView lblDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        lblTask= (TextView) findViewById(R.id.lblTask);
        lblDate= (TextView) findViewById(R.id.lblDate);
        Intent detailIntent = getIntent();
        todo=detailIntent.getParcelableExtra(TodoListFragment.TODO_ITEM);
        populateView();
    }

    private void populateView(){
        lblTask.setText(todo.getTask());
        lblDate.setText(todo.getCreatedFormated());
    }

}
