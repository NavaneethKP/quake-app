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

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        // Create a fake list of earthquake locations.
        ArrayList<earthquakes> quakelist = new ArrayList<>();
        earthquakes obj1 = new earthquakes("7.2","San Francisco","Feb 2 2016");
        quakelist.add(obj1);
        earthquakes obj2 = new earthquakes("6.1","London","July 20 2015");
        earthquakes obj3 = new earthquakes("3.9","Tokyo","Nov 10 2014");
        earthquakes obj4 = new earthquakes("5.4","Mexico City","May 3 2014");
        earthquakes obj5 = new earthquakes("2.8","Moscow","Jan 31 2013");
        earthquakes obj6 = new earthquakes("4.9","Rio de Janeiro","Aug 19 2012");
        earthquakes obj7 = new earthquakes("1.6","Paris","Oct 30 2011");
        quakelist.add(obj2);
        quakelist.add(obj3);
        quakelist.add(obj4);
        quakelist.add(obj5);
        quakelist.add(obj6);
        quakelist.add(obj7);
        ListView earthquakeListView = (ListView) findViewById(R.id.list);
        CustomAdapter adapter=new CustomAdapter(EarthquakeActivity.this,R.layout.mylist,quakelist);
        earthquakeListView.setAdapter(adapter);
    }
}



