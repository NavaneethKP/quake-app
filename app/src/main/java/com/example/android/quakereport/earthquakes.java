package com.example.android.quakereport;

/**
 * Created by kpn on 12/6/17.
 */

public class earthquakes {

    private String magnitude,place,date;

    public earthquakes(String magnitude, String place, String date) {
        this.magnitude = magnitude;
        this.place = place;
        this.date = date;
    }


    public String getMagnitude() {
        return magnitude;
    }

    public String getPlace() {
        return place;
    }

    public String getDate() {
        return date;
    }
}
