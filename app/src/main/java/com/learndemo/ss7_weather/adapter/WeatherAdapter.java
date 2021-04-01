package com.learndemo.ss7_weather.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.learndemo.ss7_weather.R;
import com.learndemo.ss7_weather.model.Weather;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter {
    private Activity activity;
    private List<Weather> list;

    public WeatherAdapter(Activity activity,List<Weather> list){
        this.activity = activity;
        this.list = list;
    }
    public void reloadData(List<Weather> list){
        this.list = list;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = activity.getLayoutInflater().inflate(R.layout.weather_list,parent,false);
        WeatherHolder holder = new WeatherHolder(itemView);
        return  holder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        WeatherHolder hd = (WeatherHolder) holder;
        Weather model = list.get(position);

        hd.tvDateTime.setText(convertTime(model.getDateTime()));
        hd.tvValue.setText(String.valueOf(model.getTemperature().getValue()));
        if (model.getWeatherIcon() < 10){
            Glide.with(activity).load("https://developer.accuweather.com/sites/default/files/0" + model.getWeatherIcon() + "-s.png").into(hd.ivWeatherIcon);
        }else {
            Glide.with(activity).load("https://developer.accuweather.com/sites/default/files/" + model.getWeatherIcon() + "-s.png").into(hd.ivWeatherIcon);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class WeatherHolder extends RecyclerView.ViewHolder{
        TextView tvIconPhrase,tvValue,tvDateTime;
        ImageView ivWeatherIcon;

        public WeatherHolder(@NonNull View itemView) {
            super(itemView);

            tvValue = itemView.findViewById(R.id.tvValue);
            tvDateTime = itemView.findViewById(R.id.tvDateTime);
            ivWeatherIcon = itemView.findViewById(R.id.ivWeatherIcon);
        }
    }

    public String convertTime(String inputTime) {
        SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = null;
        try {
            date = inFormat.parse(inputTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat outFormat = new SimpleDateFormat("ha");
        String goal = outFormat.format(date);
        return goal;
    }

}
