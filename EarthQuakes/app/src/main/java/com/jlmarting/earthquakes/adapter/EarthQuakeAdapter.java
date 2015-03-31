package com.jlmarting.earthquakes.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jlmarting.earthquakes.R;
import com.jlmarting.earthquakes.model.EarthQuake;

import java.util.List;

/**
 * Created by cursomovil on 25/03/15.
 */
public class EarthQuakeAdapter extends ArrayAdapter<EarthQuake> {

    private int resource;

    public EarthQuakeAdapter(Context context, int resource, List<EarthQuake> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        RelativeLayout layout;

        // Si no tenemos la vista, la tendremos que generar
        if (convertView == null) {
            layout = new RelativeLayout(getContext());
            LayoutInflater li;
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            li = (LayoutInflater) getContext().getSystemService(inflater);
            li.inflate(resource, layout, true);
        } else {
            layout = (RelativeLayout) convertView;
        }

        EarthQuake item = getItem(position);
        TextView tvMag = (TextView) layout.findViewById(R.id.tvMag);
        TextView tvPlace = (TextView) layout.findViewById(R.id.tvPlace);
        TextView tvDate = (TextView) layout.findViewById(R.id.tvDate);


        //Asignamos valores a la vista
        tvMag.setText(String.valueOf(item.getMagnitude()));
        tvPlace.setText(item.getPlace());
        tvDate.setText(item.getDate().toString());

        return layout;
    }


}
