package com.sandesha.midtermnew;

import java.io.Serializable;

public class Cities implements Serializable {
    String city;
    String country;

    public Cities() {
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Cities{" +
                "city='" + city + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
