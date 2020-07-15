package com.sandesha.midtermnew;

import java.io.Serializable;

public class CitiesDescForecast implements Serializable {
 String time;
 String temp;
 String humidity;
 String icon;
 String desc;

    public CitiesDescForecast() {
    }

    @Override
    public String toString() {
        return "CitiesDescForecast{" +
                "time='" + time + '\'' +
                ", temp='" + temp + '\'' +
                ", humidity='" + humidity + '\'' +
                ", icon='" + icon + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
