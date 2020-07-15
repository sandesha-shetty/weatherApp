package com.sandesha.midtermnew;

import java.io.Serializable;

public class CitiesDesc implements Serializable {
    String temp;
    String temp_min;
    String temp_max;
    String description;
    String humidity;
    String speed;
    String icon;

    public CitiesDesc() {
    }

    @Override
    public String toString() {
        return "CitiesDesc{" +
                "temp='" + temp + '\'' +
                ", temp_min='" + temp_min + '\'' +
                ", temp_max='" + temp_max + '\'' +
                ", description='" + description + '\'' +
                ", humidity='" + humidity + '\'' +
                ", speed='" + speed + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }
}
