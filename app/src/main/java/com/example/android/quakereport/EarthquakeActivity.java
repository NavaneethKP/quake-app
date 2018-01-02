/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.app.LoaderManager;
import android.content.Loader;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<earthquakes>> {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    //Sample USGS query
    private static final String SAMPLE_QUERY ="https://earthquake.usgs.gov/fdsnws/event/1/query";

    private CustomAdapter adapter;

    private int ID=1;

    TextView emptyview;
    ListView earthquakeListView;
    ProgressBar progress_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        //Specifying id for the views
        progress_view=(ProgressBar) findViewById(R.id.progress_view);
        earthquakeListView = (ListView) findViewById(R.id.list);
        emptyview=(TextView) findViewById(R.id.emptyview);

        //To create an emptyview if there is no list item available
        earthquakeListView.setEmptyView(emptyview);


        //Create a reference for the LoaderManager
        LoaderManager loader=getLoaderManager();

        //Intialise the loader with id,bundle,activity (since it implements the interface)
        Log.i(LOG_TAG,"initLoader()");

        //Checking if there is internet connection
        //Else do not initialise the loader
        //Disable progress bar and display the text
        ConnectivityManager cm=(ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activenetwork=cm.getActiveNetworkInfo();
        boolean isconnected=activenetwork!=null && activenetwork.isConnectedOrConnecting();
        if(isconnected)
        loader.initLoader(ID,null,this);
        else
        {
            progress_view.setVisibility(View.GONE);
            emptyview.setText("No Internet Connection");

        }


    }

    @Override
    public Loader<List<earthquakes>> onCreateLoader(int id, Bundle args) {

        //To create a new loader for the query url

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String minMagnitude = sharedPreferences.getString(getString(R.string.settings_min_magnitude_key),getString(R.string.settings_min_magnitude_default));
        String orderBy = sharedPreferences.getString(getString(R.string.settings_order_by_key),getString(R.string.settings_order_by_default));

        Uri baseuri = Uri.parse(SAMPLE_QUERY);
        Uri.Builder uribuilder = baseuri.buildUpon();

        uribuilder.appendQueryParameter("format", "geojson");
        uribuilder.appendQueryParameter("limit", "10");
        uribuilder.appendQueryParameter("minmag", minMagnitude);
        uribuilder.appendQueryParameter("orderby", orderBy);

        return new EarthqaukeLoader(this, uribuilder.toString());

    }

    @Override
    public void onLoadFinished(Loader<List<earthquakes>> loader, final List<earthquakes> data) {

        //Checking if the list is null
        //Otherwise create an adapter for the objects
        //Also setOnItemClickListener to go to the website for more information

        Log.i(LOG_TAG,"onLoadFinished()");

        //Hide the loading indicator
        progress_view=(ProgressBar) findViewById(R.id.progress_view);
        progress_view.setVisibility(View.GONE);

        //Setting the emptyview to the string
        emptyview.setText("No Earthquakes Found");
        if(data!=null) {
            adapter = new CustomAdapter(EarthquakeActivity.this, R.layout.mylist, data);
            earthquakeListView.setAdapter(adapter);
            earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    earthquakes obj = data.get(i);
                    Uri webpage_url = Uri.parse(obj.getUrl());
                    Intent webpage = new Intent(Intent.ACTION_VIEW, webpage_url);
                    startActivity(webpage);

                }
            });
        }
    }

    @Override
    public void onLoaderReset(Loader<List<earthquakes>> loader) {

        //To clear the adapter with the existing data
        Log.i(LOG_TAG,"onLoaderReset()");
        adapter.clear();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.action_settings)
        {
            Intent intent = new Intent(this, settingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}



