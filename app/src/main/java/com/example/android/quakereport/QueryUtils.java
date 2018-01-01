package com.example.android.quakereport;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by kpn on 14/6/17.
 */

public class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    public QueryUtils() {
    }

    //A method to create URL object from url string
    private static URL createurl(String url_string) {
        URL url = null;
        try {
            url = new URL(url_string);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error creating the url", e);
        }
        return url;
    }

    //Method to make HttpRequest
    //Takes URL object as input
    //Checks if the url is null or not
    //If not null check if response code is 200
    //If it is 200 call the getResponse() method
    //else display a log message showing the error code
    //finally disconnect the urlconnection & also close the input stream

    private static String makeHttpRequest(URL url) throws IOException {
        String JSON_RESPONSE = "";
        InputStream inputstream = null;
        if (url == null) {
            return JSON_RESPONSE;
        }

        HttpURLConnection url_connection = null;
        try {
            url_connection = (HttpURLConnection) url.openConnection();
            url_connection.setReadTimeout(10000);
            url_connection.setConnectTimeout(15000);
            url_connection.setRequestMethod("GET");
            url_connection.connect();

            if (url_connection.getResponseCode() == 200) {
                inputstream = url_connection.getInputStream();
                JSON_RESPONSE = getResponse(inputstream);
                return JSON_RESPONSE;

            } else Log.e(LOG_TAG, "Error Code" + url_connection.getResponseCode());

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error in the url", e);
        } finally {
            if (url_connection != null)
                url_connection.disconnect();
            if (inputstream != null)
                inputstream.close();
        }

        return JSON_RESPONSE;

    }

    //Method to getResponse From input stream of bytes
    //Checks  if input is null
    //if not null extract the string line by line and add to StringBuilder
    //Convert the stringBuilder to string and return it

    private static String getResponse(InputStream input) throws IOException {


        StringBuilder output = new StringBuilder();
        if(input!=null) {
            InputStreamReader inputstreamreader = new InputStreamReader(input, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputstreamreader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
            return output.toString();
        }
        return null;
    }

    //Method to extract feature from jsonstring
    //Check if string is empty else parse the json response
    //Since we gave limit as 10 run the loop 10 times and add each object to the list
    //return the List of earthquakes
    private static List<earthquakes> extractFeature(String jsonstring) {
        List<earthquakes> earthquakes = new ArrayList<>();
        if (TextUtils.isEmpty(jsonstring))
            return null;

        JSONObject root = null;
        try {
            root = new JSONObject(jsonstring);
            JSONArray features = root.getJSONArray("features");
            for (int i = 0; i < 10; i++) {
                JSONObject object = features.getJSONObject(i);
                JSONObject properties = object.getJSONObject("properties");
                double magnitude = properties.getDouble("mag");
                String place = properties.getString("place");
                long time = properties.getLong("time");
                String url = properties.getString("url");
                earthquakes.add(new earthquakes(magnitude, place, time, url));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return earthquakes;
    }

    //Calls Method to create URL object
    //Calls the makeHttpRequest method
    //Calls extractFeature method
    //Returns the earthquakes list

    public static List<earthquakes> extractearthquakes(String query) {

        //Create the URL object
        Log.i(LOG_TAG,"extractearthquakes()");
        URL url = createurl(query);
        String json_response = "";
        List<earthquakes> earthquakes = new ArrayList<>();
        try {
            json_response = makeHttpRequest(url);
            earthquakes = extractFeature(json_response);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error in closing the input stream", e);
        }

        return earthquakes;

    }
}
