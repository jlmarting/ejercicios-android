package com.jlmarting.todolistfragment.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jlmarting.todolistfragment.R;
import com.jlmarting.todolistfragment.model.ToDo;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by cursomovil on 23/03/15.
 * Estamos creando un adaptador "a medida", dado que queremos un ArrayAdapter de la
 * clase que hemos creado, ToDo.
 * El adaptador comunica estructuras de datos con elementos de la vista. En esta implementación
 * diremos cómo se hace.
 */
public class ToDoAdapter extends ArrayAdapter <ToDo>{

    private int resource;


    public ToDoAdapter(Context context, int resource, List<ToDo> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LinearLayout layout;

        // Si no tenemos la vista, la tendremos que generar
        if( convertView == null) {
            layout= new LinearLayout(getContext());
            LayoutInflater li;
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            li=(LayoutInflater) getContext().getSystemService(inflater);
            li.inflate(resource, layout, true);
        }
        else {
            layout = (LinearLayout) convertView;
        }

        ToDo item = getItem(position);
        TextView lblTask = (TextView) layout.findViewById(R.id.lblTask);
        TextView lblDate = (TextView) layout.findViewById(R.id.lblDate);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

        //Asignamos valores a la vista
        lblTask.setText(item.getTask());
        lblDate.setText(sdf.format(item.getCreated()));

        return layout;
    }
}
