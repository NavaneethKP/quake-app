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

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<earthquakes>> {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    //Sample USGS query
    private static final String SAMPLE_QUERY ="https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";

    private CustomAdapter adapter;

    private int ID=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        //Create a reference for the LoaderManager
        LoaderManager loader=getLoaderManager();

        //Intialise the loader with id,bundle,activity (since it implements the interface)
        loader.initLoader(ID,null,this);

    }

    @Override
    public Loader<List<earthquakes>> onCreateLoader(int id, Bundle args) {

        //To create a new loader for the query url
        return new EarthqaukeLoader(this,SAMPLE_QUERY);
    }

    @Override
    public void onLoadFinished(Loader<List<earthquakes>> loader, final List<earthquakes> data) {

        //Checking if the list is null
        //Otherwise create an adapter for the objects
        //Also setOnItemClickListener to go to the website for more information

        if(data!=null) {
            ListView earthquakeListView = (ListView) findViewById(R.id.list);
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
        adapter.clear();

    }
}



