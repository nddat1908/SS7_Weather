package com.learndemo.ss7_weather.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.learndemo.ss7_weather.ApiWeatherManager;
import com.learndemo.ss7_weather.R;
import com.learndemo.ss7_weather.adapter.WeatherAdapter;
import com.learndemo.ss7_weather.model.Weather;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvListWeather;
    List<Weather> listData;
    WeatherAdapter adapter;
    TextView tvValue,tvIconPhrase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvIconPhrase = findViewById(R.id.tvIconPhrase);
        tvValue = findViewById(R.id.tvValue);

        getListData();

        listData = new ArrayList<>();
        adapter = new WeatherAdapter(MainActivity.this,listData);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);

        rvListWeather = findViewById(R.id.rvListWeather);
        rvListWeather.setLayoutManager(layoutManager);
        rvListWeather.setAdapter(adapter);
    }

    private void getListData(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiWeatherManager.SERVER)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiWeatherManager service = retrofit.create(ApiWeatherManager.class);
        service.getListData().enqueue(new Callback<List<Weather>>() {
            @Override
            public void onResponse(Call<List<Weather>> call, Response<List<Weather>> response) {
                if (response.body() != null){
                    listData = response.body();
                    adapter.reloadData(listData);
                    tvIconPhrase.setText(listData.get(0).getIconPhrase());
                    tvValue.setText(String.valueOf(listData.get(0).getTemperature().getValue())+ "º");
                }
            }

            @Override
            public void onFailure(Call<List<Weather>> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Fail",Toast.LENGTH_LONG).show();
            }
        });
    }

}