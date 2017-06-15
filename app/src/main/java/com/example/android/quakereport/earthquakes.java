package com.example.android.quakereport;

import java.math.BigInteger;

/**
 * Created by kpn on 12/6/17.
 */

public class earthquakes {

    private String place;
    private double magnitude;
    private long date;

    public earthquakes(double magnitude, String place, long date) {
        this.magnitude = magnitude;
        this.place = place;
        this.date = date;
    }


    public double getMagnitude() { return magnitude; }

    public String getPlace() {
        return place;
    }

    public long getDate() {
        return date;
    }
}
