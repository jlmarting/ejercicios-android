package com.jlmarting.earthquakes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jlmarting.earthquakes.R;
import com.jlmarting.earthquakes.model.EarthQuake;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by cursomovil on 25/03/15.
 */
public class earthQuakesAdapter extends ArrayAdapter <EarthQuake> {

    private int resource;
    public earthQuakesAdapter(Context context, int resource, List<EarthQuake> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }






    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LinearLayout layout;



        if( convertView == null)
        {
            layout= new LinearLayout(getContext());

            LayoutInflater li;
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            li=(LayoutInflater) getContext().getSystemService(inflater);
            li.inflate(resource, layout, true);


        }   else {

            layout = (LinearLayout) convertView;
        }

        EarthQuake item = getItem(position);
        TextView lbldist = (TextView) layout.findViewById(R.id.lbldist);
        TextView lblsite = (TextView) layout.findViewById(R.id.lblsite);
        TextView lbltime = (TextView) layout.findViewById(R.id.lbltime);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");


       // String item3= item.getMagnitude().toString();
        //lbltime.setText(item3);
        lbldist.setText(Double.toString(item.getMagnitude()));
        lblsite.setText(item.getPlace());
        lbltime.setText(sdf.format(item.getTime()));

        return layout;
    }
}

