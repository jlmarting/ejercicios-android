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

    private TextView lblDist;
    private TextView lblSite;
    private TextView lblTime;

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
        this.lblDist = (TextView) layout.findViewById(R.id.lbldist);
        this.lblSite = (TextView) layout.findViewById(R.id.lblsite);
        this.lblTime = (TextView) layout.findViewById(R.id.lbltime);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

        this.lblDist.setText(Double.toString(item.getMagnitude()));
        this.lblSite.setText(item.getPlace());
        this.lblTime.setText(sdf.format(item.getTime()));

        return layout;
    }
}

