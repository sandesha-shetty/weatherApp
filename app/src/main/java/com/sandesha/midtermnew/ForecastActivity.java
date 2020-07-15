package com.sandesha.midtermnew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ForecastActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    CityForeCastAdapter cityForeCastAdapter;
   ArrayList<CitiesDescForecast> result= new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);
        Intent intent = getIntent();
        final Cities cities = (Cities) intent.getSerializableExtra("citynameweather");
       // Toast.makeText(this, ""+cities, Toast.LENGTH_SHORT).show();
        setTitle("Weather Forecast" + cities.city +',' + cities.country);
        recyclerView = findViewById(R.id.recycleView);
        new GetWeatherDescForecast().execute("http://api.openweathermap.org/data/2.5/forecast?q="+cities.city+','+cities.country+"&appid=d90cd4540746eb5a7f2f8056383a93e7");

    }

    public class GetWeatherDescForecast extends AsyncTask<String,Void,ArrayList<CitiesDescForecast> >{
        ProgressDialog progressDialog = new ProgressDialog(ForecastActivity.this);
        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Downloading");
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(ArrayList<CitiesDescForecast> citiesDescForecasts) {
            progressDialog.dismiss();
            layoutManager = new LinearLayoutManager(ForecastActivity.this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setNestedScrollingEnabled(false);

            cityForeCastAdapter= new CityForeCastAdapter(result);
            recyclerView.setAdapter(cityForeCastAdapter);
        }

        @Override
        protected ArrayList<CitiesDescForecast> doInBackground(String... strings) {
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
                    JSONArray jsonArray = root.getJSONArray("list");
                    for(int i=0;i<jsonArray.length();i++){
                            CitiesDescForecast citiesDescForecast = new CitiesDescForecast();
                            citiesDescForecast.temp = jsonArray.getJSONObject(i).getJSONObject("main").getString("temp");
                            citiesDescForecast.humidity = jsonArray.getJSONObject(i).getJSONObject("main").getString("humidity");
                            citiesDescForecast.desc = jsonArray.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description");
                            citiesDescForecast.time = jsonArray.getJSONObject(i).getString("dt_txt");
                            citiesDescForecast.icon = jsonArray.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("icon");
                            result.add(citiesDescForecast);
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if(connection!=null){
                    connection.disconnect();
                }
            }
            return result;
        }
        }
    }




