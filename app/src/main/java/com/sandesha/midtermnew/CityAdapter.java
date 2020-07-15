package com.sandesha.midtermnew;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CityAdapter extends ArrayAdapter<Cities> {
    public CityAdapter(@NonNull Context context, int resource, @NonNull List<Cities> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Cities cities=getItem(position);
        if(convertView==null)
        {
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.activity_single_item,parent,false);
        }
        TextView cityName=(TextView) convertView.findViewById(R.id.tv_city);
        cityName.setText(cities.city + ',' +cities.country);
        return convertView;
    }
}
