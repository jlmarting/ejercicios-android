package com.jlmarting.earthquakes.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jlmarting.earthquakes.R;
import com.jlmarting.earthquakes.model.EarthQuake;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class EarthQuakeArrayAdapter extends ArrayAdapter<EarthQuake>{

    private int resource;

    public EarthQuakeArrayAdapter(Context context, int resource, ArrayList<EarthQuake> objects) {
        super(context, resource, objects);

        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layout;

        if(convertView==null){
            //Si no existe la vista, la creamos
            layout = new LinearLayout(getContext());

            LayoutInflater li;
            String inflater = Context.LAYOUT_INFLATER_SERVICE;

            li = (LayoutInflater) getContext().getSystemService(inflater);
            li.inflate(resource, layout, true);
        }
        else{
            layout = (LinearLayout) convertView;
        }


        EarthQuake item = getItem(position);

        TextView lblMagnitude = (TextView) layout.findViewById(R.id.magnitude);
        TextView lblPlace = (TextView) layout.findViewById(R.id.place);
        TextView lblDate = (TextView) layout.findViewById(R.id.date);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        lblMagnitude.setText(String.format("%.2f",item.getMagnitude()));
        lblPlace.setText(item.getPlace());
        lblDate.setText(sdf.format(item.getDate()));

        int n = (int) item.getMagnitude() * 10;
        int color = Color.rgb((255*n)/100, (255*(100-n))/100, 0);
        lblMagnitude.setBackgroundColor(color);


        return layout;
    }
}
