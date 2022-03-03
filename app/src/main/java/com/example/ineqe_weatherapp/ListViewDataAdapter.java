package com.example.ineqe_weatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/*
* Created custom list adapter to populate the forecast list. Receives DailyWeather Forecast
* list.
* */
public class ListViewDataAdapter extends ArrayAdapter<DailyWeather> {

    private static  final String TAG = "ForecastListAdapter";
    private Context mContext;
    int mResourcce;


    public ListViewDataAdapter(@NonNull Context context, int resource, @NonNull ArrayList<DailyWeather> objects) {
        super(context, resource, objects);
        mContext = context;
        mResourcce = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        DailyWeather d = getItem(position);

        String day = d.getDay();
        String windData = d.getWind_speed() + "MPH, " + d.getWind_direction();
        String minMax = "L: " + d.getMin() + "°C H: " + d.getMax() + "°C";

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResourcce, parent, false);

        TextView currentDay = (TextView) convertView.findViewById(R.id.currentDay);
        TextView windSpeed = (TextView) convertView.findViewById(R.id.windSpeed);
        TextView minMaxData = (TextView) convertView.findViewById(R.id.minMax);

        currentDay.setText(day);
        windSpeed.setText(windData);
        minMaxData.setText(minMax);

        return convertView;

    }
}

