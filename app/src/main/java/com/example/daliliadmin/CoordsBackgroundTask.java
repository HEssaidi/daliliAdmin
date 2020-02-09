package com.example.daliliadmin;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * HJR Es-saidi
 *
 * **/

public class CoordsBackgroundTask extends AsyncTask<Void, Void, String> {

    URL json_url_coords;
    MapsActivity mainActivity;
    HttpURLConnection httpURLConnection;
    public static Double Latitude, Longitude;
    public  Double[] lat_zones;
    public  Double[] long_zones;
    public  LatLng[] latLngs;
    String[] name_zones;
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;


    public CoordsBackgroundTask(MapsActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    protected void onPreExecute() {
        // json_url_coords = "http://10.11.129.207/daliliAdmin/test.php";
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            // Enter URL address where your php file resides
            json_url_coords = new URL("http://192.168.1.101/daliliAdmin/test.php");

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return e.toString();
        }
        try {

            // Setup HttpURLConnection class to send and receive data from php
            httpURLConnection = (HttpURLConnection) json_url_coords.openConnection();
            httpURLConnection.setReadTimeout(READ_TIMEOUT);
            httpURLConnection.setConnectTimeout(CONNECTION_TIMEOUT);
            httpURLConnection.setRequestMethod("GET");

            // setDoOutput to true as we recieve data from json file
            httpURLConnection.setDoOutput(true);

        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return e1.toString();
        }

        try {

            int response_code = httpURLConnection.getResponseCode();

            // Check if successful connection made
            if (response_code == HttpURLConnection.HTTP_OK) {

                // Read data sent from server
                InputStream input = httpURLConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                StringBuilder result = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                // Pass data to onPostExecute method
                return (result.toString());

            } else {

                return ("unsuccessful");
            }

        } catch (IOException e) {
            e.printStackTrace();
            return e.toString();
        } finally {
            httpURLConnection.disconnect();
        }

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        try {
            loadIntoListView(result);
            //mainActivity=new MapsActivity();
           if (loadIntoListView(result)!=null && name_zones!=null)
           {
               for (int i = 0; i < loadIntoListView(result).length; i++) {
               MapsActivity.drawingCercle(loadIntoListView(result)[i].latitude,loadIntoListView(result)[i].longitude,name_zones[i]);
           }

           }



        } catch (JSONException e) {
            e.printStackTrace();
        }
        Toast.makeText(mainActivity, "" + result, Toast.LENGTH_LONG).show();
    }

     ////getting results from php file
    private  LatLng [] loadIntoListView(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        name_zones = new String[jsonArray.length()];
        lat_zones = new Double[jsonArray.length()];
        long_zones = new Double[jsonArray.length()];
        latLngs = new LatLng[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            name_zones[i] = obj.getString("nom_zone");
            lat_zones[i]=obj.getDouble("lat_zone");
            long_zones[i]=obj.getDouble("long_zone");
            latLngs[i]=new LatLng(lat_zones[i],long_zones[i]);

            Log.i("string is ",name_zones[i]+" lat and long "+latLngs[i]);
        }
        return latLngs;
    }
}