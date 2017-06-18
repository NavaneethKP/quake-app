package com.example.android.quakereport;

import java.math.BigInteger;

/**
 * Created by kpn on 12/6/17.
 */

public class earthquakes {

    private String place,url;
    private double magnitude;
    private long date;

    public earthquakes(double magnitude, String place, long date,String url) {
        this.magnitude = magnitude;
        this.place = place;
        this.date = date;
        this.url=url;
    }


    public double getMagnitude() { return magnitude; }

    public String getPlace() { return place;   }

    public long getDate() { return date;  }

    public String getUrl() { return url;  }
}
