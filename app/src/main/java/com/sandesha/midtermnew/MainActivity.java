package com.sandesha.midtermnew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String citiesJson;
    ListView listView;
    ArrayList<Cities> result = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Select City");
        listView = findViewById(R.id.listView);
        citiesJson = loadJSONFromAsset(this);
        Log.d("demo",citiesJson);
        try {
            JSONObject root = new JSONObject(citiesJson);
            JSONArray data = root.getJSONArray("data");
            for(int i=0;i<data.length();i++){
                Cities cities = new Cities();
                cities.city = data.getJSONObject(i).getString("city");
                cities.country = data.getJSONObject(i).getString("country");
                result.add(cities);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        CityAdapter cityAdapter = new CityAdapter(MainActivity.this,R.layout.activity_single_item,result);

        listView.setAdapter(cityAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this,CurrentWeatherActivity.class);
                intent.putExtra("cityname",result.get(i));
                startActivity(intent);
            }
        });



    }
    public String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("cities.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }
    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected() ||
                (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                        && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
            return false;
        }
        return true;
    }
}
