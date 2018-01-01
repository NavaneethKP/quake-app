package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;


/**
 * Created by kpn on 28/6/17.
 */

public class EarthqaukeLoader extends AsyncTaskLoader<List<earthquakes>> {

    private String LOG_TAG=EarthqaukeLoader.class.getSimpleName();
    private final String url;

    //Constructor
    public EarthqaukeLoader(Context context, String url) {
        super(context);
        this.url=url;
    }

    @Override
    protected void onStartLoading() {

        Log.i(LOG_TAG,"onStartLoading()");
        forceLoad();
    }

    @Override
    public List<earthquakes> loadInBackground() {

        //To check if there was no input params
        Log.i(LOG_TAG,"loadInBackground()");
        if(url==null) {
            return null;
        }
        List<earthquakes> list = QueryUtils.extractearthquakes(url);
        return list;
    }
}
