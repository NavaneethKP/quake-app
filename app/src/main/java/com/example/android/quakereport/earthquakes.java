package com.example.android.quakereport;

/**
 * Created by kpn on 12/6/17.
 */

public class earthquakes {

    private String place;
    private double magnitude;
    private Long date;

    public earthquakes(double magnitude, String place, Long date) {
        this.magnitude = magnitude;
        this.place = place;
        this.date = date;
    }


    public double getMagnitude() { return magnitude; }

    public String getPlace() {
        return place;
    }

    public Long getDate() {
        return date;
    }
}
