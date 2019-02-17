package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    public EarthquakeAdapter(Context context, int resource, List<Earthquake> objects) {
        super(context, resource, objects);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;

        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.earthquake_list_item, parent, false);
        }

        Earthquake currentEarthquake = getItem(position);

        // Magnitude
        TextView magnitudeTextView = listItemView.findViewById(R.id.magnitudeTextView);
        String magnitude = formatMagnitude(currentEarthquake.getMagnitude());
        magnitudeTextView.setText(magnitude);

        // Magnitude background
        GradientDrawable magnitudeBackground = (GradientDrawable) magnitudeTextView.getBackground();
        magnitudeBackground.setColor(getMagnitudeColor(currentEarthquake.getMagnitude()));

        // Primary Location
        TextView primaryLocationTextView = listItemView.findViewById(R.id.primaryLocationTextView);
        String primaryLocation = getPrimaryLocation(currentEarthquake.getLocation());
        primaryLocationTextView.setText(primaryLocation);

        // Location Offset
        TextView locationOffsetTextView = listItemView.findViewById(R.id.locationOffsetTextView);
        String locationOffset = getLocationOffset(currentEarthquake.getLocation());
        locationOffsetTextView.setText(locationOffset);

        Date dateTime = new Date(currentEarthquake.getTime());

        // Date
        TextView dateTextView = listItemView.findViewById(R.id.dateTextView);
        dateTextView.setText(formatDate(dateTime));

        // Time
        TextView timeTextView = listItemView.findViewById(R.id.timeTextView);
        timeTextView.setText(formatTime(dateTime));

        return listItemView;
    }

    private String formatDate(Date date)
    {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM DD, yyyy");
        return dateFormatter.format(date);
    }

    private String formatTime(Date date)
    {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("h:mm a");
        return dateFormatter.format(date);
    }

    private String getLocationOffset(String location)
    {
        final String separator = "of";

        String locationOffset = "";

        if(location.toLowerCase().contains(separator))
        {
            Integer separatorIndex = location.toLowerCase().indexOf(separator);

            locationOffset = location.substring(0, separatorIndex + 2);
        }

        return locationOffset;
    }

    private String getPrimaryLocation(String location)
    {
        final String separator = "of";

        String primaryLocation = location;

        if(location.toLowerCase().contains(separator))
        {
            Integer separatorIndex = location.toLowerCase().indexOf(separator);

            primaryLocation = location.substring(separatorIndex + 3);
        }

        return primaryLocation;
    }

    private String formatMagnitude(double magnitude)
    {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }

    private int getMagnitudeColor(double magnitude)
    {
        int magnitudeColorResourceId;

        int magnitudeFloor = (int) Math.floor(magnitude);

        switch (magnitudeFloor)
        {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;

            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;

            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;

            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;

            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;

            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;

            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;

            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;

            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;

            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }

        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }
}
