package com.sandesha.midtermnew;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CityForeCastAdapter extends RecyclerView.Adapter<CityForeCastAdapter.ViewHolder> {
    ArrayList<CitiesDescForecast> mdata;

    public CityForeCastAdapter(ArrayList<CitiesDescForecast> mData) {

        this.mdata = mData;
    }



    @NonNull
    @Override
    public CityForeCastAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.result_layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CityForeCastAdapter.ViewHolder holder, int position) {
        CitiesDescForecast citiesDescForecast = mdata.get(position);
        PrettyTime p = new PrettyTime();
        String sdate = citiesDescForecast.time;
        Date date1 = new Date();
        try{
            date1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sdate);
        }catch (ParseException e) {
            e.printStackTrace();
        }


        // holder.tempdate.setText(citiesDescForecast.time);
        holder.tempdate.setText(p.format(date1));
        holder.temp.setText(citiesDescForecast.temp);
        holder.humidity.setText(citiesDescForecast.humidity);
        holder.desc.setText(citiesDescForecast.desc);
        Picasso.get().load("http://openweathermap.org/img/wn/"+citiesDescForecast.icon+"@2x.png").into(holder.iv);
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tempdate,temp,humidity,desc;
        ImageView iv;
        //ConstraintLayout constraintLayout;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            tempdate = itemView.findViewById(R.id.tv_forecast);
            temp = itemView.findViewById(R.id.tv_tempvalforecast);
            humidity = itemView.findViewById(R.id.tv_humidityvalforecast);
            desc = itemView.findViewById(R.id.tv_descforecast);
            iv = itemView.findViewById(R.id.imageViewforecast);
        }

    }
}
