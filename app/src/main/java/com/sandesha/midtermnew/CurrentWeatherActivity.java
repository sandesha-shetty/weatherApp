package com.sandesha.midtermnew;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.apache.commons.io.IOUtils;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CurrentWeatherActivity extends AppCompatActivity {
        TextView tv_city;
        ImageView imageView;
        TextView tv_tempval;
        TextView tv_tempMaxval;
        TextView tv_tempMinval;
        TextView tv_descval;
        TextView tv_humidityVal;
        TextView tv_windSpeedval;
        Button btn_forecast;
        CitiesDesc citiesDesc = new CitiesDesc();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_weather);
        Intent intent = getIntent();
        final Cities cities = (Cities) intent.getSerializableExtra("cityname");
       // Toast.makeText(this, ""+cities, Toast.LENGTH_SHORT).show();
        setTitle("Current Weather"+cities.city +',' + cities.country);
        tv_city =findViewById(R.id.tv_citySel);
        imageView = findViewById(R.id.imageView);
        tv_tempval = findViewById(R.id.tv_tempval);
        tv_tempMaxval = findViewById(R.id.tv_tempmaxval);
        tv_tempMinval = findViewById(R.id.tv_tempminVal);
        tv_descval = findViewById(R.id.tv_descval);
        tv_humidityVal = findViewById(R.id.tv_humidityval);
        tv_windSpeedval = findViewById(R.id.tv_windspeedval);
        tv_city.setText(cities.city + ',' +cities.country);
        btn_forecast = findViewById(R.id.btn_forecast);
        new GetWeatherDesc().execute("http://api.openweathermap.org/data/2.5/weather?q="+cities.city+','+cities.country+"&appid=d90cd4540746eb5a7f2f8056383a93e7");
        btn_forecast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CurrentWeatherActivity.this,ForecastActivity.class);
                intent.putExtra("citynameweather",cities);
                startActivity(intent);
                finish();
            }
        });


    }
    public class GetWeatherDesc extends AsyncTask<String,Void,CitiesDesc>{
        ProgressDialog progressDialog = new ProgressDialog(CurrentWeatherActivity.this);

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Downloading Details");
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(CitiesDesc citiesDesc) {
            Toast.makeText(CurrentWeatherActivity.this, ""+citiesDesc, Toast.LENGTH_SHORT).show();

            progressDialog.dismiss();
            tv_tempval.setText(citiesDesc.temp);
            tv_tempMaxval.setText(citiesDesc.temp_max);
            tv_tempMinval.setText(citiesDesc.temp_min);
            tv_descval.setText(citiesDesc.description);
            tv_humidityVal.setText(citiesDesc.humidity);
            tv_windSpeedval.setText(citiesDesc.speed);
            Picasso.get().load("http://openweathermap.org/img/wn/"+citiesDesc.icon+"@2x.png").into(imageView);

        }

        @Override
        protected CitiesDesc doInBackground(String... strings) {
            HttpURLConnection connection = null;
            try{
                URL url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                Log.e("url",url.toString());
                connection.connect();
                if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                    String json = IOUtils.toString(connection.getInputStream(), "UTF-8");
                    Log.d("demos", "result" + json);
                    JSONObject root = new JSONObject(json);
                    JSONObject main = root.getJSONObject("main");
                    JSONObject wind = root.getJSONObject("wind");
                    JSONArray weather = root.getJSONArray("weather");
                    citiesDesc.temp = main.getString("temp") + "F";
                    citiesDesc.temp_min = main.getString("temp_min") + "F";
                    citiesDesc.temp_max = main.getString("temp_max") + "F";
                    citiesDesc.humidity = main.getString("humidity") + '%';
                    citiesDesc.description = weather.getJSONObject(0).getString("description");
                    citiesDesc.speed = wind.getString("speed") + "miles/hr";
                    citiesDesc.icon = weather.getJSONObject(0).getString("icon");
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if(connection!=null){
                    connection.disconnect();
                }
            }
            return citiesDesc;

        }


    }
}
