package com.example.android.quakereport;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class EarthQuakeAdapter extends ArrayAdapter<EarthQuake> {

    public EarthQuakeAdapter(Context context, int resource, ArrayList<EarthQuake> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;

        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.earthquake_item, parent, false);
        }

        EarthQuake currentEarthquake = getItem(position);

        TextView magnitudeTextView = listItemView.findViewById(R.id.magnitudeTextView);
        magnitudeTextView.setText(String.valueOf(currentEarthquake.getMagnitude()));

        TextView locationTextView = listItemView.findViewById(R.id.locationTextView);
        locationTextView.setText(currentEarthquake.getLocation());

        TextView dateTextView = listItemView.findViewById(R.id.dateTextView);
        dateTextView.setText(String.valueOf(currentEarthquake.getDate()));

        return listItemView;
    }
}
